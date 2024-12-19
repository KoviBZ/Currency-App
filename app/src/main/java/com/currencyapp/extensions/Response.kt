package com.currencyapp.extensions

import android.util.Log
import com.currencyapp.network.repo.Resource
import retrofit2.Response

fun <T, R> Response<T>.handleResponse(
    onSuccess: (body: T) -> R,
    onError: (Response<T>) -> Resource<R> = { handleErrorResponse() }
) = if (isSuccessful) {
    val body = body()
    if (body == null) {
        Resource.Error("Cannot parse response")
    } else {
        Resource.Success(onSuccess(body))
    }
} else {
    onError.invoke(this)
}

fun <T, R> Response<T>.handleErrorResponse(): Resource<R> {
    val errorMessage = errorBody()?.string() ?: "An error occurred"
    Log.e("ResourceError", errorMessage)
    return Resource.Error(errorMessage)
}