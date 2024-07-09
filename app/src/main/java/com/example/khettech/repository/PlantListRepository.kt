package com.example.khettech.repository

import com.example.khettech.base.ApiPlantsInterface
import com.example.khettech.di.MyHiltModule.Companion.API_KEY
import com.example.khettech.network.SafeApiCall
import javax.inject.Inject

class PlantListRepository @Inject constructor(
    private val plantsInterface: ApiPlantsInterface
) : SafeApiCall {

    suspend fun getPlantList(page: Int) = safeApiCall {
        plantsInterface.getPlantList(API_KEY, page)
    }
}