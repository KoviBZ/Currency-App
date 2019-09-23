package com.currencyapp.utils

import androidx.annotation.DrawableRes
import com.currencyapp.R

class CountryConverter {

    companion object {

        //STUB
        @DrawableRes
        fun getImageForCountry(code: String): Int {
            return when(code) {
                "EUR" -> R.color.tmpEUR
                "USD" -> R.color.tmpUSD
                "GBP" -> R.color.tmpGBP
                "PLN" -> R.color.tmpPLN
                "AUD" -> R.color.tmpAUD
                "CHF" -> R.color.tmpCHF
                else -> android.R.color.black
            }
        }
    }
}