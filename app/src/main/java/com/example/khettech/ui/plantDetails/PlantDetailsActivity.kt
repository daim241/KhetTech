package com.example.khettech.ui.plantDetails

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.khettech.R
import com.example.khettech.base.BaseActivity
import com.example.khettech.databinding.ActivityPlantDetailsBinding
import com.example.khettech.network.UIStateResponse
import com.example.khettech.ui.plantDetails.adapter.PlantDetailsAdapter
import com.example.khettech.ui.plantDetails.viewModel.PlantDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityPlantDetailsBinding

    private val viewModel by viewModels<PlantDetailsViewModel>()
    private lateinit var plantDetailsAdapter: PlantDetailsAdapter

    private var plantDetailId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityPlantDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.plantDetailsRecView.apply {
            plantDetailsAdapter = PlantDetailsAdapter(ArrayList())
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = plantDetailsAdapter
        }

        intent.extras?.let { extra ->
            if (extra.containsKey("plantDetails")) {
                plantDetailId = extra.getInt("plantDetails")
            } else {
                Log.d("TestingDaim", "First Log")
                Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()
                finish()
            }
        } ?: run {
            Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()
            finish()
        }

        if (plantDetailId != -1) {
            loadDetails(plantDetailId)
        }

    }

    private fun loadDetails(id: Int) {
        viewModel.getPlantDetails(id)

        //Observe API call response
        viewModel.plantDetailsLiveData?.observe(this, Observer {
            when (it) {
                UIStateResponse.Loading -> {
                    showProgressBar()
                }

                is UIStateResponse.Success -> {
                    plantDetailsAdapter.addData(it.data)
                    dismissProgressBar()
                }

                is UIStateResponse.Failure -> {
                    Toast.makeText(this, it.errorMsg, Toast.LENGTH_SHORT).show()
                    dismissProgressBar()
                }
            }
        })
    }
}