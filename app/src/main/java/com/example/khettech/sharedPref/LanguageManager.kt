package com.example.khettech.sharedPref

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageManager {

    private const val LANGUAGE_PREFERENCE_KEY = "language_preference"

    fun setLanguagePreference(context: Context, language: String) {
        val sharedPreferences = context.getSharedPreferences(LANGUAGE_PREFERENCE_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("language", language).apply()
    }

    fun getLanguagePreference(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(LANGUAGE_PREFERENCE_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString("language", "en") ?: "en"
    }

    fun updateLanguage(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
}