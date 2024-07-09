package com.example.khettech.network

import android.util.Log
import com.example.khettech.utils.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

interface SafeApiCall {
    suspend fun <T : Any> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    //TODO return error code and msg
                    Resource.Failure("Something Went Wrong")
                }
            } catch (throwable: Throwable) {
                Log.d("TestingDaim", "throwable -> ${throwable.message}")
                when (throwable) {
                    is NoInternetException -> {
                        Resource.Failure(throwable.message.toString())
                    }
                    //Exception for an unexpected, non-2xx HTTP response.
                    is HttpException -> {
                        Resource.Failure(throwable.message().toString())
                    }
                    //For unknown error
                    else -> {
                        Resource.FailureUnknown(throwable.message.toString())
                    }
                }
            }
        }
    }
}