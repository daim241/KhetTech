package com.example.khettech.ui.tips

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.khettech.R
import com.example.khettech.base.BaseActivity
import com.example.khettech.databinding.ActivityTipsBinding
import kotlin.random.Random


class TipsActivity : BaseActivity() {

    private lateinit var binding: ActivityTipsBinding
    private lateinit var tipsMessage: TextView

    private val messages = arrayOf(
        R.string.message_1,R.string.message_2, R.string.message_3, R.string.message_4, R.string.message_5,
        R.string.message_6, R.string.message_7, R.string.message_8, R.string.message_9, R.string.message_10,
        R.string.message_11, R.string.message_12, R.string.message_13, R.string.message_14, R.string.message_15,
        R.string.message_16, R.string.message_17

        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tipsMessage = binding.messageTextView

        binding.iconImageView.setOnClickListener {
            displayRandomMessage()
        }
        binding.messageHint.setOnClickListener{
            displayRandomMessage()
        }

    }

    private fun displayRandomMessage() {
        val randomIndex = Random.nextInt(messages.size)
        val messageResId = messages[randomIndex]
        val message = getString(messageResId)
        tipsMessage.text = message
    }
}