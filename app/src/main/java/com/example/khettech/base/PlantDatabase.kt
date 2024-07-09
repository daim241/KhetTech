package com.example.khettech.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.khettech.dao.PlantJournalDAO
import com.example.khettech.dao.ReportDao
import com.example.khettech.models.ModelPlantJournal
import com.example.khettech.models.ModelReport


@Database(
    entities = [ModelPlantJournal::class, ModelReport::class],
    version = 2,
    exportSchema = false
)
abstract class PlantDatabase : RoomDatabase() {

    abstract fun plantJournalDao(): PlantJournalDAO
    abstract fun reportDao(): ReportDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        fun getDatabase(context: Context): PlantDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java,
                    "khetTech_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
