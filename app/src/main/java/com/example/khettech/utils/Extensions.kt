package com.example.khettech.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

fun String.toDesireFormat(format: String, desireFormat: String): String {
    val inputFormat = SimpleDateFormat(format, Locale.ENGLISH)
    val outputFormat = SimpleDateFormat(desireFormat, Locale.ENGLISH)

    return try {
        val date = inputFormat.parse(this.toString())
        return outputFormat.format(date)
    } catch (e: ParseException) {
        ""
    }
}
