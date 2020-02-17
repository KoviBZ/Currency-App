package com.currencyapp.utils.callback

import com.currencyapp.network.entity.RateDto

interface ItemMovedCallback {
    fun onItemMoved(itemOnTop: RateDto)
}