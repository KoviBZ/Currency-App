package com.currencyapp.network

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable): Resource<T> {
            return Resource(Status.FAILURE, null, error.message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

    }
}