package com.example.khettech.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "plant_details_table")
data class PlantDetailsResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val common_name: String,
    val origin: List<String>,
    val type: String,
    val dimension: String,
    val propagation: List<String>,
    val pruning_month: List<String>,
    val edible_leaf: Boolean,
    val cuisine: Boolean,
    val medicinal: Boolean,
    val description: String,
    val default_image: DetailsImage,
    val watering_general_benchmark: WateringGeneral
) : Parcelable

@Parcelize
data class WateringGeneral(
    val value: String,
    val unit: String
) : Parcelable

@Parcelize
data class DetailsImage(
    val imageId: Int,
    val license: Int,
    val license_name: String,
    val license_url: String,
    val original_url: String,
    val regular_url: String?,
    val medium_url: String,
    val small_url: String,
    val thumbnail: String
) : Parcelable