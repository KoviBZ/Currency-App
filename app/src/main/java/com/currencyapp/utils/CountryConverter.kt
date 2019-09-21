package com.currencyapp.utils

import androidx.annotation.DrawableRes
import com.currencyapp.R

class CountryConverter {

    companion object {

        //STUB
        @DrawableRes
        fun getImageForCountry(code: String): Int {
            return R.drawable.ic_launcher_foreground
        }
    }
}