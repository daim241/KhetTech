package com.example.khettech

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KhetTechApplicationClass: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}