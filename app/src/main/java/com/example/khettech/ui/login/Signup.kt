package com.example.khettech.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.khettech.ui.home.HomeActivity
import com.example.khettech.R
import com.example.khettech.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_signup)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val user = binding.signinTxt
        user.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val pass = binding.userPassword.text.toString()
            val name = binding.userName.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty()) {
                if (isValidEmail(email) && isValidPassword(pass)) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    task.exception?.message ?: "Unknown error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Invalid email or password format", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

}

