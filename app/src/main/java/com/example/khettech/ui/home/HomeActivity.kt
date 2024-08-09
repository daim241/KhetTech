package com.example.khettech.ui.home

import android.app.Dialog
import android.content.Intent
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.khettech.R
import com.example.khettech.base.BaseActivity
import com.example.khettech.databinding.ActivityHomeBinding
import com.example.khettech.sharedPref.LanguageManager
import com.example.khettech.ui.diseaseDetection.DiseaseDetrction
import com.example.khettech.ui.gardeningTip.GardeningTips
import com.example.khettech.ui.journal.Journal
import com.example.khettech.ui.plantList.PlantList
import com.example.khettech.ui.plantNursery.PlantNursery
import com.example.khettech.ui.slider.ImageSliderAdapter
import com.example.khettech.ui.tips.TipsActivity
import com.example.khettech.ui.userProfile.Profile
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class HomeActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var database: DatabaseReference
    private lateinit var motionSensorRef: DatabaseReference
    private lateinit var motionSensorListener: ValueEventListener
    private lateinit var query: Query
    private var isMotionDetected = false
    private var isSwitchOn = true
    private lateinit var ringtone: Ringtone
    private lateinit var adapter: ImageSliderAdapter
    private val timer = Timer()
    private var beepingJob: Job? = null
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter = ImageSliderAdapter(this)
        binding.viewPager.adapter = adapter
        timer.scheduleAtFixedRate(SliderTimer(), 2000, 2000)

        database = FirebaseDatabase.getInstance().reference
        query = database.child("khettech")

        motionSensorRef = database.child("khettech").child("motion")

        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        ringtone = RingtoneManager.getRingtone(applicationContext, notification)

        binding.temperatureContainer.setOnClickListener(this)
        binding.humidityContainer.setOnClickListener(this)
        binding.journalContainer.setOnClickListener(this)
        binding.plantSearchContainer.setOnClickListener(this)
        binding.tipsContainer.setOnClickListener(this)
        binding.imageContainer.setOnClickListener(this)
        binding.userProfile.setOnClickListener(this)
        binding.imageContainer.setOnClickListener(this)
        binding.videoContainer.setOnClickListener(this)
        binding.nurseryContainer.setOnClickListener(this)
        binding.exit.setOnClickListener(this)

        binding.motionSensorIcon.isChecked = LanguageManager.getToggleState(this@HomeActivity);

        if (LanguageManager.getToggleState(this@HomeActivity)) {
            startMotionSensorListener()
        }


        binding.motionSensorIcon.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    LanguageManager.setToggleState(this@HomeActivity, true)
                    //Toast.makeText(this@HomeActivity, LanguageManager.getToggleState(this@HomeActivity).toString(), Toast.LENGTH_SHORT).show()
                    startMotionSensorListener()
                } else {
                    LanguageManager.setToggleState(this@HomeActivity, false)
                    //Toast.makeText(this@HomeActivity, LanguageManager.getToggleState(this@HomeActivity).toString(), Toast.LENGTH_SHORT).show()
//                    stopAlertBeep()
                    stopMotionSensorListener()
                }
//            }

        }

    }

    inner class SliderTimer : TimerTask() {
        override fun run() {
            runOnUiThread {
                val nextItem =
                    if (binding.viewPager.currentItem == adapter.count - 1) 0 else binding.viewPager.currentItem + 1
                binding.viewPager.currentItem = nextItem
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.temperature_container -> {
                showProgressBar("Please wait while we get the temperature")
                openDialogTemperature()
            }

            R.id.humidity_container -> {
                showProgressBar("Please wait while we get the humidity and soil moisture")
                openDialogHumidity()
            }

            R.id.image_container -> {
                val intent = Intent(this, DiseaseDetrction::class.java)
                startActivity(intent)
            }

            R.id.journal_container -> {
                val intent = Intent(this, Journal::class.java)
                startActivity(intent)
            }

            R.id.plant_search_container -> {
                val intent = Intent(this, PlantList::class.java)
                startActivity(intent)

            }

            R.id.tips_container -> {
                val intent = Intent(this, TipsActivity::class.java)
                startActivity(intent)
            }

            R.id.user_profile -> {
                val bottomSheetFragment = Profile()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }

            R.id.video_container -> {
                val intent = Intent(this, GardeningTips::class.java)
                startActivity(intent)
            }

            R.id.nursery_container -> {
                val intent = Intent(this, PlantNursery::class.java)
                startActivity(intent)
            }

            R.id.exit -> {
                finishAffinity()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun openDialogTemperature() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_iot_temperature)
        val dataTextView: TextView = dialog.findViewById(R.id.data_text_view)
        val dataRef = database.child("khettech").child("temperature")
        val tempRef = dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.child("temperature").getValue()
                dismissProgressBar()
                dataTextView.text = getString(R.string.temperature) + ": $data °C"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TestingDaim", "Cancel dialog ${databaseError}")
                Toast.makeText(this@HomeActivity, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        })
        query.addListenerForSingleValueEvent(tempRef)

        dialog.show()
    }


    private fun openDialogHumidity() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_iot_humidity)
        val humidityTextView: TextView = dialog.findViewById(R.id.humidity_text_view)
        val soilTextView: TextView = dialog.findViewById(R.id.soil_text_view)
        val dataRef = database.child("khettech")

        val humidityRef = dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val humidityData = dataSnapshot.child("humidity").getValue()
                val soilMoistureData = dataSnapshot.child("soil_moisture").getValue()
                dismissProgressBar()

                humidityTextView.text = getString(R.string.humidity) + ": $humidityData %"
                soilTextView.text = getString(R.string.soil_moisture) + ": $soilMoistureData %"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@HomeActivity, R.string.no_data_found, Toast.LENGTH_SHORT).show()
            }
        })

        query.addListenerForSingleValueEvent(humidityRef)

        dialog.show()
    }


    private fun startMotionSensorListener() {
        Toast.makeText(this@HomeActivity, R.string.msg_motion_senesor_on, Toast.LENGTH_SHORT).show()

        motionSensorListener = motionSensorRef.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.P)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val motionDetected = dataSnapshot.getValue(Int::class.java)//.child("motion").getValue()
                val switchState = binding.motionSensorIcon.isChecked

                if (LanguageManager.getToggleState(this@HomeActivity)) {
                    if (motionDetected == 1) {
//                        Toast.makeText(this@HomeActivity, motionDetected.toString(), Toast.LENGTH_SHORT).show()

                        Toast.makeText(
                            this@HomeActivity,
                            R.string.msg_motion_detected,
                            Toast.LENGTH_SHORT
                        ).show()
                        //playAlertBeep()
                        ringtone.isLooping = true;
                        ringtone.play()

                    }
                    else {
                        isMotionDetected = false
//                        Toast.makeText(this@HomeActivity, motionDetected.toString(), Toast.LENGTH_SHORT).show()
                        ringtone.stop()
                        //stopAlertBeep()
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@HomeActivity, R.string.no_data_found, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun startBeeping() {
        if (beepingJob == null) {
            beepingJob = lifecycleScope.launch {
                while (true) {
                    playAlertBeep()
                    delay(20000) // Delay for 0.5 seconds
                }
            }
        }
    }

    private fun stopBeeping() {
        beepingJob?.cancel()
        beepingJob = null
        stopAlertBeep()
    }

    private fun playAlertBeep() {
        if (isSwitchOn && isVolumeOn()) {
            if (!::ringtone.isInitialized) {
                val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                ringtone = RingtoneManager.getRingtone(applicationContext, notification)
            }
            ringtone.play()
        }
    }

    private fun stopAlertBeep() {
        if (!::ringtone.isInitialized) {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            ringtone = RingtoneManager.getRingtone(applicationContext, notification)

        }
        ringtone.stop()
    }


    private fun stopMotionSensorListener() {
        motionSensorRef.removeEventListener(motionSensorListener)
        stopAlertBeep()
    }

    private fun isVolumeOn(): Boolean {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        return audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION) > 0
    }
}