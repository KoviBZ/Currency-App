package com.currencyapp.network.entity

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: LinkedHashMap<String, Double>
)