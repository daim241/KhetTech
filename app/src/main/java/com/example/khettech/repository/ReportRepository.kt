package com.example.khettech.repository

import com.example.khettech.base.PlantDatabase
import com.example.khettech.models.ModelReport
import javax.inject.Inject

class ReportRepository @Inject constructor(
    private val reportDatabase: PlantDatabase
) {
    suspend fun insert(description: ModelReport) {
        reportDatabase.reportDao().insert(description)
    }
}