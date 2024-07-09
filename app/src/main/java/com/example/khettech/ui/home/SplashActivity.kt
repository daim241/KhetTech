package com.example.khettech.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.example.khettech.ui.login.Signup
import com.example.khettech.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val delayMillis = 1000L

        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = firebaseAuth.currentUser
            val intent = if (currentUser != null) {
                Intent(this, HomeActivity::class.java)
            } else {
                Intent(this, Signup::class.java)
            }
            startActivity(intent)
            finish()
        }, delayMillis)
    }
}

