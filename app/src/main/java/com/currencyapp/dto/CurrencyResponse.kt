package com.currencyapp.dto

data class CurrencyResponse(
    val base: String,
    val date: String, // 2018-09-06
    val rates: List<RateDto>
)

//@StringDef(
//
//)
//class CurrencyCode {
//    EUR("EUR")
//}