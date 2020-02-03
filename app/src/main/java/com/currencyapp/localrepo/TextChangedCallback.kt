package com.currencyapp.localrepo

interface TextChangedCallback {

    fun onTextChanged(currency: String, changedMultiplier: Double)
}