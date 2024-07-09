package com.example.khettech.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report_table")
data class ModelReport(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var report_description: String = ""
)