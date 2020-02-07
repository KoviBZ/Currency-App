package com.currencyapp.ui.main.model

import com.currencyapp.network.entity.RateDto
import io.reactivex.Single

interface MainModel {
    fun retrieveCurrencyResponse(currency: String): Single<List<RateDto>>
}