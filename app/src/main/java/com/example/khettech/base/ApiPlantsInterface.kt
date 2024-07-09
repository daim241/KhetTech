package com.example.khettech.base

import com.example.khettech.models.PlantDetailsResponse
import com.example.khettech.models.PlantResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiPlantsInterface {

    @GET("species-list")
    suspend fun getPlantList(
        @Query("key") apiKey: String,
        @Query("page") page: Int
    ):
            Response<PlantResponse>

    @GET("species/details/{ID}")
    suspend fun getPlantDetails(
        @Path("ID") ID: Int,
        @Query("key") apiKey: String
    ):
            Response<PlantDetailsResponse>
}

