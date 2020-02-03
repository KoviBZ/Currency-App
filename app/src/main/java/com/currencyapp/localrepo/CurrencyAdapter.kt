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
import androidx.recyclerview.widget.RecyclerView
import com.currencyapp.R
import com.mynameismidori.currencypicker.ExtendedCurrency
import java.text.DecimalFormat

class CurrencyAdapter(
    private val context: Context,
    private val onItemMovedCallback: OnItemMovedCallback,
    private val textChangedCallback: TextChangedCallback
) : RecyclerView.Adapter<CurrencyAdapter.RateViewHolder>() {

    private var currencyList = ArrayList<RateDto>()
    private var currencyRateMap = HashMap<String, RateDto>()

    private var multiplier: Double = 1.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.row_currency_adapter,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = currencyList.size

    private fun getRateAtPosition(position: Int): RateDto? {
        return currencyRateMap[currencyList[position].key]
    }

    fun setItemsList(newList: ArrayList<RateDto>) {
        if (currencyList.isEmpty()) {
            currencyList.addAll(newList)
        }

        for (currency in newList) {
            currencyRateMap[currency.key] = (currency)
        }

        notifyItemRangeChanged(1, currencyList.size - 1, multiplier)
    }

    fun updateRates(newMultiplier: Double) {
        this.multiplier = newMultiplier

        notifyItemRangeChanged(0, currencyList.size - 1, multiplier)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        getRateAtPosition(position)?.let {
            holder.bind(it)
        }
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int, payloads: List<Any>) {
        getRateAtPosition(position)?.let {
            if (payloads.isNotEmpty()) {
                holder.bind(it)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    inner class RateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val countryImageView by lazy { view.findViewById<ImageView>(R.id.country_iv) }
        private val currencyCodeTextView by lazy { view.findViewById<TextView>(R.id.currency_code_tv) }
        private val currencyNameTextView by lazy { view.findViewById<TextView>(R.id.currency_name_tv) }
        private val currencyValueEditText by lazy { view.findViewById<EditText>(R.id.currency_value_et) }

        private var symbol = ""

        fun bind(rate: RateDto) {

            if (symbol != rate.key) {
                initView(rate)
                this.symbol = rate.key
            }

            // If the EditText holds the focus, we don't change the value
            if (!currencyValueEditText.isFocused) {
                val formatter = DecimalFormat("#0.00")
                currencyValueEditText.setText(formatter.format(rate.value * multiplier).toString())
            } else {
//                currencyValueEditText.removeTextChangedListener()
            }
        }

        private fun initView(rateDto: RateDto) {
            val currencyObj = ExtendedCurrency.getCurrencyByISO(rateDto.key)
            countryImageView.setImageResource(currencyObj.flag)

            currencyCodeTextView.text = rateDto.key
            currencyNameTextView.text = currencyObj.name

            val formatter = DecimalFormat("#0.00")
            currencyValueEditText.setText(formatter.format(rateDto.value * multiplier))
            currencyValueEditText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    //If view lost focus, we do nothing
                    if (!hasFocus) {
                        return@OnFocusChangeListener
                    }

                    layoutPosition.takeIf { it != 0 }?.also { currentPosition ->

                        currencyList.removeAt(currentPosition).also {
                            currencyList.add(0, it)
                        }

                        notifyItemMoved(currentPosition, 0)
                    }
                }

            currencyValueEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(changedText: Editable?) {
                    val changedString = changedText.toString()

                    if (currencyValueEditText.isFocused && changedString.isNotEmpty()) {
                        multiplier = changedString.toDouble()
                        textChangedCallback.onTextChanged(rateDto.key, multiplier)
                    }
                }

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

            })
        }
    }
}