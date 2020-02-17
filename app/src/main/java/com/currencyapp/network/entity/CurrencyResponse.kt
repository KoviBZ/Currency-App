package com.currencyapp.network.entity

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("baseCurrency") val baseCurrency: String,
    @SerializedName("rates") val rates: LinkedHashMap<String, Double>
)