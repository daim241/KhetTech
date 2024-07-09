package com.example.khettech.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.khettech.models.ModelPlantJournal

@Dao
interface PlantJournalDAO {

    @Insert
    suspend fun insert(plantJournal: ModelPlantJournal)

    @Query("SELECT * FROM journal_table")
    fun getAllPlants(): LiveData<MutableList<ModelPlantJournal>>

    @Update
    suspend fun update(journal: ModelPlantJournal)

    @Delete
    suspend fun delete(journal: ModelPlantJournal)

}