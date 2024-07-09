package com.example.khettech.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "journal_table")
data class ModelPlantJournal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String? = "",
    var description: String? = "",
    var image: String?,
    var date: String?
)
