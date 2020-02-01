package com.currencyapp.localrepo

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.currencyapp.R
import com.currencyapp.utils.CurrencyDiffCallback
import com.mynameismidori.currencypicker.ExtendedCurrency
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class CurrencyAdapter(
    private val context: Context,
    private val onItemMovedCallback: OnItemMovedCallback,
    private val textChangedCallback: TextChangedCallback
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var itemsList = ArrayList<RateDto>()

    private var multiplier: Double = 1.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.row_currency_adapter,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = itemsList.size

    fun setItemsList(newList: ArrayList<RateDto>) {
        val diffCallback = CurrencyDiffCallback(itemsList, newList)
        val result = DiffUtil.calculateDiff(diffCallback)
        result.dispatchUpdatesTo(this)

        this.itemsList = newList
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(
            itemsList[position],
            textChangedCallback
        )
    }

    inner class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val countryImageView by lazy { view.findViewById<ImageView>(R.id.country_iv) }
        private val currencyCodeTextView by lazy { view.findViewById<TextView>(R.id.currency_code_tv) }
        private val currencyNameTextView by lazy { view.findViewById<TextView>(R.id.currency_name_tv) }
        private val currencyValueEditText by lazy { view.findViewById<EditText>(R.id.currency_value_et) }

        fun bind(
            rateDto: RateDto,
            textChangedCallback: TextChangedCallback
        ) {
            val currencyObj = ExtendedCurrency.getCurrencyByISO(rateDto.key)
            countryImageView.setImageResource(currencyObj.flag)

            currencyCodeTextView.text = rateDto.key

            currencyNameTextView.text = currencyObj.name

            val formatter = DecimalFormat("#.00")
            currencyValueEditText.setText(formatter.format(rateDto.value * multiplier))
            currencyValueEditText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    //If view lost focus, we do nothing
                    if (!hasFocus) {
                        return@OnFocusChangeListener
                    }

                    layoutPosition.takeIf { it != 0 }?.also { currentPosition ->

                        itemsList.removeAt(currentPosition).also {
                            itemsList.add(0, it)
                        }

                        notifyItemMoved(currentPosition, 0)
                    }

                    //TODO podmień value 0go ze zmienionym
                }
            currencyValueEditText.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(changedText: Editable?) {
                    val changedString = changedText.toString()

                    if(currencyValueEditText.hasFocus()) {
                        multiplier = changedString.toDouble()

                        notifyItemRangeChanged(1, itemsList.size, multiplier)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    //intentionally left empty
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //intentionally left empty
                }

            })
        }
    }
}