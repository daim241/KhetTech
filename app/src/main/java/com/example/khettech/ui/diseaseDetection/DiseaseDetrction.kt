package com.example.khettech.ui.diseaseDetection

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.media.ThumbnailUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.khettech.R
import com.example.khettech.databinding.ActivityDiseaseDetrctionBinding
import com.example.khettech.databinding.ActivityHomeBinding
import com.example.khettech.ml.Model
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder


class DiseaseDetrction : AppCompatActivity() {

    private lateinit var binding: ActivityDiseaseDetrctionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityDiseaseDetrctionBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.camera.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 3)
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }

        binding.gallery.setOnClickListener {
            val cameraIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(cameraIntent, 1)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                3 -> {
                    val image = data?.extras?.get("data") as Bitmap
                    val dimension = minOf(image.width, image.height)
                    val thumbnailImage =
                        ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                    binding.imageView.setImageBitmap(thumbnailImage)

                    val scaledImage = Bitmap.createScaledBitmap(thumbnailImage, 256, 256, false)
                    classifyImage(scaledImage)
                }

                else -> {
                    val imageUri = data?.data
                    var image: Bitmap? = null
                    try {
                        image = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    binding.imageView.setImageBitmap(image)

                    val scaledImage = Bitmap.createScaledBitmap(image!!, 256, 256, false)
                    classifyImage(scaledImage)
                }
            }
        }
    }

    fun classifyImage(image: Bitmap) {
        try {
            val model = Model.newInstance(applicationContext)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)

            // Convert Bitmap to ByteBuffer
            val byteBuffer = ByteBuffer.allocateDirect(4 * 256 * 256 * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val resizedBitmap = Bitmap.createScaledBitmap(image, 256, 256, true)
            val intValues = IntArray(256 * 256)
            resizedBitmap.getPixels(intValues, 0, resizedBitmap.width, 0, 0, resizedBitmap.width, resizedBitmap.height)
            var pixel = 0
            for (i in 0 until 256) {
                for (j in 0 until 256) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((((`val` shr 16) and 0xFF) * (1.0f / 255))) // Red
                    byteBuffer.putFloat((((`val` shr 8) and 0xFF) * (1.0f / 255)))  // Green
                    byteBuffer.putFloat(((`val` and 0xFF) * (1.0f / 255)))           // Blue
                }
            }

            // Load ByteBuffer to input tensor buffer
            inputFeature0.loadBuffer(byteBuffer)

            // Process the input tensor buffer
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.getOutputFeature0AsTensorBuffer()
            val confidences = outputFeature0.floatArray

            // Find the class with the highest confidence
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf(
                "Healthy. \n Your green friend is in good condition.",
                "Healthy. \n Your green friend is in good condition.",
                "Leaf Scorch. " +
                        "\nDescription: \nLeaf scorch is a non-infectious, physiological condition caused by unfavorable environmental situations. It is not caused by fungus, bacteria, or virus. " +
                        "The problem may appear on almost any plant if weather conditions are favorable, such as high temperatures, dry winds, and low soil moisture. " +
                        "\n Prevention Measures: \nProper watering: Make sure to water your plants deeply and regularly, especially during dry periods. Avoid overwatering or allowing the soil to remain waterlogged. ",

                "Healthy. \nYour green friend is in good condition.",
                "Early Blight. " +
                        "\nDescription:  Early blight is one of the most common tomato and potato diseases, occurring nearly every season in Minnesota.\n" +
                        "It affects leaves, fruits and stems and can be severely yield-limiting when susceptible tomato cultivars are used and weather is favorable. " +
                        "\nPrevention Measures:Cover the soil under the plants with mulch, such as fabric, straw, plastic mulch, or dried leaves. Water at the base of each plant, using drip irrigation, a soaker hose, or careful hand watering." +
                        " Pruning the bottom leaves can also prevent early blight spores from splashing up from the soil onto leaves.",
                "Late Blight. " +
                        "\nDescription Late blight is a potentially devastating disease of tomato and potato, infecting leaves, stems, tomato fruit, and potato tubers.\n" +
                        "The disease spreads quickly in fields and can result in total crop failure if untreated. Late blight first appears on the lower, older leaves as water-soaked, gray-green spots. " +
                        "\nPrevention Measures:If there is visible late blight infestation it is recommended to apply fungicides with a spore-killing effect mainly.",
                "Healthy. \nYour green friend is in good condition.",
                "Bacterial Spot. " +
                        "\nDescription: Bacterial spot is caused by four species of Xanthomonas and occurs worldwide wherever tomatoes or peppers are grown. Bacterial spot causes leaf and fruit spots, which leads to defoliation, sun-scalded fruit, and yield loss" +
                        "\nPrevention Measures:Use clean propagation material and disease-free seed.\n" +
                        "Remove sources of inoculum such as dead leaves.\n" +
                        "In field-grown tomato and pepper rotate fields to prevent infection from volunteer plants and crop debris.\n" +
                        "Avoid waste piles in the vicinity of the greenhouse or field.",


                "Powdery Mildew. " +
                        "\nDescription: Powdery mildew is one of the most widespread and easily recognized plant diseases. Powdery mildews are characterized by spots or patches of white to grayish, talcum-powder-like growth. Powdery mildews are severe in warm, dry climates. Many plants have been developed to be resistant or tolerant to powdery mildew." +
                        "\nPrevention Measures:Sulfur dust is effective against many powdery mildews but should not be applied in hot weather. A number of other organic treatments, including copper-based fungicides, baking soda solutions, and neem oil, have also proven effective.",

                "Powdery Mildew. " +
                        "\nDescription: Powdery mildew is one of the most widespread and easily recognized plant diseases. Powdery mildews are characterized by spots or patches of white to grayish, talcum-powder-like growth. Powdery mildews are severe in warm, dry climates. Many plants have been developed to be resistant or tolerant to powdery mildew." +
                        "\nPrevention Measures:Sulfur dust is effective against many powdery mildews but should not be applied in hot weather. A number of other organic treatments, including copper-based fungicides, baking soda solutions, and neem oil, have also proven effective.",

//                "Blight. " +
//                        "\nDescription: Blight spreads by fungal spores that are carried by insects, wind, water and animals from infected plants, and then deposited on soil. The disease requires moisture to progress, so when dew or rain comes in contact with fungal spores in the soil, they reproduce." +
//                        "Measures for controlling and preventing blights typically involve the destruction of the infected plant parts" +
//                        "\nPrevention Measures: Use of disease-free seed or stock and resistant varieties; crop rotation; pruning and spacing of plants for better air circulation; controlling pests that carry the fungus from plant to plant.",
                "Early Blight. " +
                        "\nDescription:  Early blight is one of the most common tomato and potato diseases, occurring nearly every season in Minnesota.\n" +
                        "It affects leaves, fruits and stems and can be severely yield-limiting when susceptible tomato cultivars are used and weather is favorable. " +
                        "\nPrevention Measures:Cover the soil under the plants with mulch, such as fabric, straw, plastic mulch, or dried leaves. Water at the base of each plant, using drip irrigation, a soaker hose, or careful hand watering." +
                        " Pruning the bottom leaves can also prevent early blight spores from splashing up from the soil onto leaves.",

                "Blight. " +
                        "\nDescription: Blight spreads by fungal spores that are carried by insects, wind, water and animals from infected plants, and then deposited on soil. The disease requires moisture to progress, so when dew or rain comes in contact with fungal spores in the soil, they reproduce." +
                        "\nPrevention Measures:Measures for controlling and preventing blights typically involve the destruction of the infected plant parts; use of disease-free seed or stock and resistant varieties; crop rotation; pruning and spacing of plants for better air circulation; controlling pests that carry the fungus from plant to plant.",

            )


            val text = classes[maxPos]
            val sentences = text.split("\n")
            val builder = SpannableStringBuilder()

            sentences.forEach { sentence ->
                val parts = sentence.split("\n")
                builder.append("${parts[0]}\n")
                if (parts.size > 1) {
                    builder.append("${parts[1]}\n")
                }
                if (parts.size > 2) {
                    val descriptionIndex = builder.length
                    builder.append("${parts[2]}\n")
                    builder.setSpan(
                        StyleSpan(Typeface.BOLD),
                        descriptionIndex,
                        descriptionIndex + parts[2].length,
                        SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE
                    )
                }
                if (parts.size > 3) {
                    val preventionIndex = builder.length
                    builder.append("${parts[3]}\n")
                    builder.setSpan(
                        StyleSpan(Typeface.BOLD),
                        preventionIndex,
                        preventionIndex + parts[3].length,
                        SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE
                    )
                }
                builder.append("\n")
            }
            binding.result.text = builder
            model.close()

        } catch (e: IOException) {
            Log.e("classifyImage", "Error processing image: ${e.message}")
        }
    }
}