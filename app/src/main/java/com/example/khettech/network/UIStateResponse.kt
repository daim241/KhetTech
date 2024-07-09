package com.example.khettech.network

sealed class UIStateResponse<out T> {
    //If Api successfully hit.
    data class Success<out T>(val data: T) : UIStateResponse<T>()

    //If any Api Error or Internet Error
    data class Failure(
        val errorMsg: String
    ) : UIStateResponse<Nothing>()

    //Api is in progress
    object Loading : UIStateResponse<Nothing>()
}