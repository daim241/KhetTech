package com.example.khettech.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.khettech.models.ModelPlantJournal
import com.example.khettech.models.ModelPlantList
import com.example.khettech.models.PlantDetailsResponse

@Dao
interface PlantListDao {
    @Insert
    suspend fun insertPlantList(plantList: List<ModelPlantList>)

    @Query("SELECT * FROM plant_list_table")
    fun getAllPlantsList(): LiveData<MutableList<ModelPlantList>>
}