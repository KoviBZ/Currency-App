package com.currencyapp.network.entity

import com.currencyapp.localrepo.CurrencyResponse

sealed class CurrencyHttpResponse {

    data class Success(
        val currencyResponse: CurrencyResponse
    )

    data class Failure(
        val error: Throwable
    )
}