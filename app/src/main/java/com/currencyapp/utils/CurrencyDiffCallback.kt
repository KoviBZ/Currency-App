package com.currencyapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.currencyapp.localrepo.RateDto


class CurrencyDiffCallback(
    private val newList: List<RateDto>,
    private val oldList: List<RateDto>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val areTheSame = oldList[oldItemPosition] == newList[newItemPosition]
        return areTheSame
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}