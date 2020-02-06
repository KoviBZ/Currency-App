package com.currencyapp.ui.main.model

import com.currencyapp.localrepo.RateDto
import io.reactivex.Single

interface MainModel {
    fun retrieveCurrencyResponse(currency: String): Single<ArrayList<RateDto>>
}