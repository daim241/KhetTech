package com.example.khettech.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.khettech.R
import com.example.khettech.databinding.ActivityMainBinding
import com.example.khettech.sharedPref.LanguageManager
import com.example.khettech.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


//        binding.userName.setText("xyz@gmail.com")
//        binding.userPassword.setText("test123")

        binding.btnLogin.setOnClickListener {
            val email = binding.userName.text.toString()
            val pass = binding.userPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, R.string.msg_login_successfull, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                R.string.email_or_password_is_incorrect,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, R.string.please_fill_in_both_email_and_password, Toast.LENGTH_SHORT)
                    .show()
            }
        }


        val newUser = binding.signInTxt
        newUser.setOnClickListener {
            val signIntent = Intent(this, Signup::class.java)
            startActivity(signIntent)
        }

        binding.urduTxt.setOnClickListener{
            val currentLanguage = LanguageManager.getLanguagePreference(this)
            val newLanguage = if (currentLanguage == "en") "ur" else "en"
            LanguageManager.setLanguagePreference(this, newLanguage)
            LanguageManager.updateLanguage(this, newLanguage)
            recreate()
        }

        binding.forgetPassword.setOnClickListener {
            val intent = Intent(this, ForgetPassword::class.java)
            startActivity(intent)
        }
    }

}

