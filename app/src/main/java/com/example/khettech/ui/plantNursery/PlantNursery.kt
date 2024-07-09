package com.example.khettech.ui.plantNursery

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.khettech.R
import com.example.khettech.base.BaseActivity
import com.example.khettech.databinding.ActivityPlantNurseryBinding
import com.example.khettech.models.ModelPlantNursery
import com.example.khettech.ui.plantNursery.adapter.PlantNurseryAdapter

class PlantNursery : BaseActivity() {

    private lateinit var binding: ActivityPlantNurseryBinding
    private lateinit var nurseryAdapter: PlantNurseryAdapter
    private lateinit var nurseryList: ArrayList<ModelPlantNursery>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityPlantNurseryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        nurseryList = ArrayList()

        // Add all nursery details
        addNurseryToList(getString(R.string.nursery_name_1), getString(R.string.nursery_address_1), getString(R.string.nursery_phone_1))
        addNurseryToList(getString(R.string.nursery_name_2), getString(R.string.nursery_address_2), getString(R.string.nursery_phone_2))
        addNurseryToList(getString(R.string.nursery_name_3), getString(R.string.nursery_address_3), getString(R.string.nursery_phone_3))
        addNurseryToList(getString(R.string.nursery_name_4), getString(R.string.nursery_address_4), getString(R.string.nursery_phone_4))
        addNurseryToList(getString(R.string.nursery_name_5), getString(R.string.nursery_address_5), getString(R.string.nursery_phone_5))
        addNurseryToList(getString(R.string.nursery_name_6), getString(R.string.nursery_address_6), getString(R.string.nursery_phone_6))
        addNurseryToList(getString(R.string.nursery_name_7), getString(R.string.nursery_address_7), getString(R.string.nursery_phone_7))
        addNurseryToList(getString(R.string.nursery_name_8), getString(R.string.nursery_address_8), getString(R.string.nursery_phone_8))
        addNurseryToList(getString(R.string.nursery_name_9), getString(R.string.nursery_address_9), getString(R.string.nursery_phone_9))
        addNurseryToList(getString(R.string.nursery_name_10), getString(R.string.nursery_address_10), getString(R.string.nursery_phone_10))
        addNurseryToList(getString(R.string.nursery_name_11), getString(R.string.nursery_address_11), getString(R.string.nursery_phone_11))
        addNurseryToList(getString(R.string.nursery_name_12), getString(R.string.nursery_address_12), getString(R.string.nursery_phone_12))
        addNurseryToList(getString(R.string.nursery_name_13), getString(R.string.nursery_address_13), getString(R.string.nursery_phone_13))
        addNurseryToList(getString(R.string.nursery_name_14), getString(R.string.nursery_address_14), getString(R.string.nursery_phone_14))

        nurseryAdapter = PlantNurseryAdapter(nurseryList)
        binding.recyclerView.adapter = nurseryAdapter

        binding.plantNurseryBack.setOnClickListener {
            Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun addNurseryToList(name: String, address: String, phone: String) {
        val nursery = ModelPlantNursery(name, address, phone)
        nurseryList.add(nursery)
    }
}
