package com.currencyapp.utils

interface TextChangedCallback {

    fun onTextChanged(currency: String, changedMultiplier: Double)
}