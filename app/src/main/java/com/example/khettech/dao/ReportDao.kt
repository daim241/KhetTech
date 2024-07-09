package com.example.khettech.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.khettech.models.ModelReport

@Dao
interface ReportDao {

    @Insert
    suspend fun insert(description: ModelReport)
}