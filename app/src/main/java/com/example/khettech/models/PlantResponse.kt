package com.example.khettech.models

import com.google.gson.annotations.SerializedName

data class PlantResponse(
    @SerializedName("data")
    var plantList: List<ModelPlantList>,
    var to: Int,
    var per_page: Int,
    var current_page: Int,
    var from: Int,
    var last_page: Int,
    var total: Int
)