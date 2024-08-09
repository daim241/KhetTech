package com.example.khettech.ui.journal

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khettech.R
import com.example.khettech.models.ModelPlantJournal
import com.example.khettech.ui.journal.adapter.JournalAdapter
import com.example.khettech.ui.journal.viewModel.JournalViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class Journal : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: JournalAdapter
    private val viewModel: JournalViewModel by viewModels()

    private val MY_CAMERA_REQUEST_CODE = 100
    private val CAMERA_REQUEST = 1888

    private var dialogView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_journal)


        adapter = JournalAdapter(this, viewModel)

        recyclerView = findViewById(R.id.journal_rec_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allPlants.observe(this) { plants ->
            adapter.setPlants(plants)
        }

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            showAddPlantDialog()
        }

        val backButton = findViewById<ImageView>(R.id.journal_back)
        backButton.setOnClickListener {
            Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun showAddPlantDialog() {
        val simpleDate = SimpleDateFormat("dd/M/yyyy")
        val currentDate = simpleDate.format(Date())

        dialogView = layoutInflater.inflate(R.layout.dialog_add_plant, null)
        val editTextPlantName = dialogView!!.findViewById<EditText>(R.id.PlantName)
        val editTextPlantDescription = dialogView!!.findViewById<EditText>(R.id.PlantDescription)
        val image = dialogView!!.findViewById<ImageView>(R.id.PlantImage)

        val dialogBuilder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
            .setTitle(R.string.add_new_plant)
            .setView(dialogView)
            .setPositiveButton(R.string.add_btn, null)
            .setNegativeButton(R.string.cancel_btn, null)
            .setNeutralButton(R.string.save, null)
            .create()

        dialogBuilder.setOnShowListener {
            val addButton = dialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE)
            val saveButton = dialogBuilder.getButton(AlertDialog.BUTTON_NEUTRAL)

            val validationAction = {
                val plantName = editTextPlantName.text.toString()
                val plantDescription = editTextPlantDescription.text.toString()
                val imageTag = image.getTag()?.toString()

                if (plantName.isBlank() && plantDescription.isBlank()) {
                    Toast.makeText(this, "Please fill at least one field", Toast.LENGTH_SHORT).show()
                } else if (imageTag == null) {
                    Toast.makeText(this, "Please capture an image first", Toast.LENGTH_SHORT).show()
                } else {
                    val newPlant = ModelPlantJournal(
                        0,
                        plantName,
                        plantDescription,
                        imageTag,
                        currentDate
                    )
                    viewModel.insert(newPlant)
                    adapter.notifyDataSetChanged()
                    dialogBuilder.dismiss()
                }
            }

            addButton.setOnClickListener {
                validationAction()
            }

            saveButton.setOnClickListener {
                validationAction()
            }
        }

        image.setOnClickListener {
            onImageViewClicked()
        }

        dialogBuilder.show()
    }


    private fun onImageViewClicked() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), MY_CAMERA_REQUEST_CODE);
        } else {
            openCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //When permission is granted open camera
                onImageViewClicked()
            } else {
                Toast.makeText(this, R.string.msg_no_camera_permission, Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === CAMERA_REQUEST && resultCode === RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap?
            val imageUri = saveImageToGallery(imageBitmap)
            dialogView!!.findViewById<ImageView>(R.id.PlantImage).setImageURI(imageUri)
            dialogView!!.findViewById<ImageView>(R.id.PlantImage).setTag(imageUri)
            Log.d("Testing Daim", "imageUri $imageUri")
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    private fun saveImageToGallery(bitmap: Bitmap?): Uri? {
        bitmap ?: return null
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, "Plant_${System.currentTimeMillis()}.jpg")
        val fos = FileOutputStream(image)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()

        MediaStore.Images.Media.insertImage(
            contentResolver,
            image.absolutePath,
            image.name,
            image.name)

        return Uri.fromFile(image)
    }
}