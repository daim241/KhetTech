package com.example.khettech.repository

import androidx.lifecycle.LiveData
import com.example.khettech.base.PlantDatabase
import com.example.khettech.models.ModelPlantJournal
import javax.inject.Inject

class JournalRepository @Inject constructor(
    private val plantJournalDatabase: PlantDatabase
) {

    val allPlants: LiveData<MutableList<ModelPlantJournal>> =
        plantJournalDatabase.plantJournalDao().getAllPlants()


    suspend fun insert(plantJournal: ModelPlantJournal) {
        plantJournalDatabase.plantJournalDao().insert(plantJournal)
    }

    suspend fun update(plantJournal: ModelPlantJournal) {
        plantJournalDatabase.plantJournalDao().update(plantJournal)
    }

    suspend fun delete(plantJournal: ModelPlantJournal) {
        plantJournalDatabase.plantJournalDao().delete(plantJournal)
    }


}