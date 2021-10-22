package dev.jaym21.cryptowatch.utils

sealed class ApiResponse<T>(val data: T ?= null, val responseMessage: String ?= null) {

    //success class means we get a response with data
    class Success<T>(data: T?): ApiResponse<T>(data)

    //failed or error class means we did not get response with data so there will be an error message
    class Error<T>(responseMessage: String, data: T? = null): ApiResponse<T>(data, responseMessage)

    //loading class for between responses
    class Loading<T> : ApiResponse<T>()
}