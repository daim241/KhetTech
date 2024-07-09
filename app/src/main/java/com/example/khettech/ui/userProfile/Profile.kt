package com.example.khettech.ui.userProfile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.example.khettech.ui.login.MainActivity
import com.example.khettech.R
import com.example.khettech.databinding.ProfileScreenBinding
import com.example.khettech.ui.userProfile.viewModel.ReportViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Profile : BottomSheetDialogFragment(), View.OnClickListener {

    private var _binding: ProfileScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels()

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        firebaseAuth = FirebaseAuth.getInstance()

        binding.contactUs.setOnClickListener(this)
        binding.aboutUs.setOnClickListener(this)
        binding.feedback.setOnClickListener(this)
        binding.logOut.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.contact_us -> {
                showReportDialog()
            }

            R.id.about_us -> {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(
                    R.string.about_us_msg
                )
                    .setPositiveButton("OK") { dialog, id ->
                        dialog.dismiss()
                    }
                val dialog = builder.create()
                dialog.show()
            }

            R.id.feedback -> {
                showRatingDialog()
            }

            R.id.log_out -> {
                Log.d("TestingDaim", "Logout Pressed")
                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun showReportDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.report_dialog, null)
        dialogBuilder.setView(dialogView)

        val reportDescription = dialogView.findViewById<EditText>(R.id.report_description)
        val submitButton = dialogView.findViewById<Button>(R.id.submit_button)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancel_button)

        val dialog = dialogBuilder.create()

        submitButton.setOnClickListener {
            val description = reportDescription.text.toString()
            if (description.isNotBlank()) {
                viewModel.insert(description)
                showMessage(getString(R.string.report_submit))
                dialog.dismiss()
            } else {
                reportDescription.error = "${R.string.enter_description}"
            }
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showRatingDialog() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val firestore = FirebaseFirestore.getInstance()

            // Enable offline persistence
            firestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()

            val userRatingDoc = firestore.collection("ratings").document(userId)

            userRatingDoc.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {
                        showDialog(getString(R.string.already_rated))
                    } else {
                        showRatingInputDialog(userRatingDoc, userId)
                    }
                } else {
                    showDialog("${R.string.rating_failed}: ${task.exception?.message}")
                }
            }
        } else {
            showDialog(getString(R.string.user_not_logged_in))
        }
    }

    private fun showRatingInputDialog(userRatingDoc: DocumentReference, userId: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_star_rating, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(dialogView)

        val star1 = dialogView.findViewById<ImageView>(R.id.star1)
        val star2 = dialogView.findViewById<ImageView>(R.id.star2)
        val star3 = dialogView.findViewById<ImageView>(R.id.star3)
        val star4 = dialogView.findViewById<ImageView>(R.id.star4)
        val star5 = dialogView.findViewById<ImageView>(R.id.star5)

        var selectedStars = 0

        val starsList = listOf(star1, star2, star3, star4, star5)
        starsList.forEachIndexed { index, star ->
            star?.setOnClickListener {
                starsList.forEachIndexed { innerIndex, innerStar ->
                    innerStar.setImageResource(
                        if (innerIndex <= index) R.drawable.star_filled
                        else R.drawable.star_empty
                    )
                }
                selectedStars = index + 1
                dialogView.findViewById<Button>(R.id.sendButton).isEnabled = selectedStars > 0
            }
        }

        val sendButton = dialogView.findViewById<Button>(R.id.sendButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
        sendButton.isEnabled = false

        val dialog = dialogBuilder.create()

        sendButton.setOnClickListener {
            val ratingData = hashMapOf(
                "userId" to userId,
                "rating" to selectedStars
            )

            userRatingDoc.set(ratingData)
                .addOnSuccessListener {
                    showDialog(getString(R.string.feedback_msg))
                }
                .addOnFailureListener { e ->
                    showDialog(getString(R.string.failed_to_saved_rating))
                }

            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(requireContext(), R.style.PlainAlertDialogTheme)
            .setMessage(message)
            .setPositiveButton(R.string.ok_btn) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}