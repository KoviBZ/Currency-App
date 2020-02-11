package com.currencyapp.utils

import com.currencyapp.network.entity.RateDto

interface ItemMovedCallback {
    fun onItemMoved(itemOnTop: RateDto)
}