package com.example.khettech.di

import android.content.Context
import androidx.room.Room
import com.example.khettech.base.ApiPlantsInterface
import com.example.khettech.base.PlantDatabase
import com.example.khettech.repository.JournalRepository
import com.example.khettech.repository.ReportRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MyHiltModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in SingletonComponent (i.e. everywhere in the application)
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        PlantDatabase::class.java,
        "khetTech_database"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideJournalRepository(plantDatabase: PlantDatabase): JournalRepository {
        return JournalRepository(plantDatabase)
    }

    @Singleton
    @Provides
    fun provideReportRepository(plantDatabase: PlantDatabase): ReportRepository {
        return ReportRepository(plantDatabase)
    }

    @Singleton
    @Provides
    fun providesPlantApiService(
        @ApplicationContext context: Context
    ): ApiPlantsInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://perenual.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient(context))
            .build()

        return retrofit.create(ApiPlantsInterface::class.java)
    }

    private fun provideOkHttpClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
//        val networkConnectionInterceptor = NetworkConnectionInterceptor(context = context)
        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(60, TimeUnit.SECONDS)
        okHttpClient.readTimeout(60, TimeUnit.SECONDS)

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient
            .addInterceptor(loggingInterceptor)
        return okHttpClient.build()
    }

    companion object {
        const val API_KEY = "sk-6wVY662c853706b4f5235" //Personal mail
//            "sk-yU2f662caa6951cec5269" // khet-tech mail
    }
}