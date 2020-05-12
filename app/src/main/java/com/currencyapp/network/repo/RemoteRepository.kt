package com.currencyapp.network.repo

import com.currencyapp.network.entity.RateDto
import io.reactivex.Single

interface RemoteRepository {
    fun getCurrencyResponse(currencyName: String): Single<List<RateDto>>
}