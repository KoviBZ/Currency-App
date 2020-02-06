package com.currencyapp.network.entity

data class CurrencyResponse(
    val base: String,
    val date: String, // 2018-09-06
    val rates: LinkedHashMap<String, Double>
)