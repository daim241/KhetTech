package com.example.khettech.ui.gardeningTip

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.khettech.R
import com.example.khettech.base.BaseActivity
import com.example.khettech.databinding.ActivityGardeningTipsBinding
import com.example.khettech.models.GardeningTip
import com.example.khettech.ui.gardeningTip.adapter.GardeningTipAdapter

class GardeningTips : BaseActivity() {

    private lateinit var binding: ActivityGardeningTipsBinding
    private lateinit var tipsAdapter: GardeningTipAdapter
    private lateinit var tipsList: ArrayList<GardeningTip>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityGardeningTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        tipsList = ArrayList()
        tipsList.add(
            GardeningTip(
                getString(R.string.tip_1_title),
                getString(R.string.tip_1_description),
                getString(R.string.tip_1_video_url),

            )
        )
        tipsList.add(
            GardeningTip(
                getString(R.string.tip_2_title),
                getString(R.string.tip_2_description),
                getString(R.string.tip_2_video_url),
            )
        )
        tipsList.add(
            GardeningTip(
                getString(R.string.tip_3_title),
                getString(R.string.tip_3_description),
                getString(R.string.tip_3_video_url),
            )
        )
        tipsList.add(
            GardeningTip(
                getString(R.string.tip_4_title),
                getString(R.string.tip_4_description),
                getString(R.string.tip_4_video_url),
            )
        )
        tipsList.add(
            GardeningTip(
                getString(R.string.tip_5_title),
                getString(R.string.tip_5_description),
                getString(R.string.tip_5_video_url),
            )
        )
        tipsList.add(
            GardeningTip(
                getString(R.string.tip_6_title),
                getString(R.string.tip_6_description),
                getString(R.string.tip_6_video_url),
            )
        )
        tipsList.add(
            GardeningTip(
                getString(R.string.tip_7_title),
                getString(R.string.tip_7_description),
                getString(R.string.tip_7_video_url),
            )
        )
        tipsList.add(
            GardeningTip(
                getString(R.string.tip_8_title),
                getString(R.string.tip_8_description),
                getString(R.string.tip_8_video_url),
            )
        )
        tipsList.add(
            GardeningTip(
                getString(R.string.tip_9_title),
                getString(R.string.tip_9_description),
                getString(R.string.tip_9_video_url),
            )
        )
        tipsList.add(
            GardeningTip(
                getString(R.string.tip_10_title),
                getString(R.string.tip_10_description),
                getString(R.string.tip_10_video_url),
            )
        )
        tipsList.add(
            GardeningTip(
                getString(R.string.tip_11_title),
                getString(R.string.tip_11_description),
                getString(R.string.tip_11_video_url),
            )
        )

        tipsAdapter = GardeningTipAdapter(this, tipsList)
        binding.recyclerView.adapter = tipsAdapter

        binding.gardeningTipsBack.setOnClickListener {
            Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}
