package com.currencyapp.utils

import android.text.TextWatcher

/**
 *  Implemented for better visibility in
 *  [com.currencyapp.ui.main.view.adapter.CurrencyAdapter.RateViewHolder]
 */
abstract class AfterChangeTextWatcher: TextWatcher {

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
        //intentionally left empty
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //intentionally left empty
    }
}