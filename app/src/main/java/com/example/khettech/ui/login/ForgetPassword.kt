package com.example.khettech.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.khettech.R
import com.example.khettech.databinding.ForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgetPassword: AppCompatActivity() {
    private lateinit var binding: ForgetPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.buttonResetPassword.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()

            if (email.isNotEmpty()) {
                sendPasswordResetEmail(email)
            } else {
                Toast.makeText(
                    this@ForgetPassword,
                    R.string.please_enter_your_email_address,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@ForgetPassword,
                        R.string.password_reset_email_sent_successfully,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ForgetPassword,
                        "${R.string.failed_to_send_reset_email}. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}