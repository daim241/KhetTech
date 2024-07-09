package com.example.khettech.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.khettech.R

abstract class BaseActivity : AppCompatActivity() {
    private var showDialog: Dialog? = null

    private fun setupDialog() {
        showDialog = Dialog(this)
        showDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        showDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        showDialog?.setCancelable(false)
        showDialog?.setContentView(R.layout.dialog_progress_bar)
    }

    fun showProgressBar(msg: String = "") {
        if (showDialog == null) {
            setupDialog()
        }
        if (!showDialog?.isShowing!!) {
            showDialog?.show()
        }
    }

    fun dismissProgressBar() {
        showDialog?.dismiss()
    }

}