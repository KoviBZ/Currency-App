package com.currencyapp.utils.callback

import com.currencyapp.network.entity.RateDto

interface OfflineCallback {
    fun saveDataForOfflineMode(list: List<RateDto>)
    fun getOfflineData()
}