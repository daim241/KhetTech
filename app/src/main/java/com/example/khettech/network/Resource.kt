package com.example.khettech.network


sealed class Resource<out T> {
    //If Api successfully hit.
    data class Success<out T>(val data: T) : Resource<T>()

    //If any Api Error or Internet Error
    data class Failure(
        val errorMsg: String?
    ) : Resource<Nothing>()

    data class FailureUnknown(var unknownError: String = "") : Resource<Nothing>()
}