package com.example.khettech.models

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "plant_list_table")
data class ModelPlantList (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val common_name: String,
    val scientific_name: List<String>,
    val cycle: String,
    val watering: String,
    val sunlight: List<String>,
    val default_image: Image
): Parcelable

@Parcelize
data class Image(
    val imageId: Int,
    val license: Int,
    val license_name: String,
    val license_url: String,
    val original_url: String,
    val regular_url: String?,
    val medium_url: String,
    val small_url: String,
    val thumbnail: String,
    val originalUrl: String
): Parcelable