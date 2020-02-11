package com.currencyapp.ui.main.view.adapter

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
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.Constants
import com.currencyapp.utils.ItemMovedCallback
import com.currencyapp.utils.TextChangedCallback
import com.mynameismidori.currencypicker.ExtendedCurrency
import java.text.DecimalFormat

class CurrencyAdapter(
    private val context: Context,
    private val textChangedCallback: TextChangedCallback,
    private val itemMovedCallback: ItemMovedCallback
) : RecyclerView.Adapter<CurrencyAdapter.RateViewHolder>() {

    private var currencyList = ArrayList<RateDto>()
    private var currencyRateMap = HashMap<String, RateDto>()

    private var multiplier: Double = Constants.DEFAULT_MULTIPLIER
    private var multiplierForOffline: Double = Constants.DEFAULT_MULTIPLIER

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
            currencyRateMap[currency.key] = currency
        }

        this.multiplierForOffline = 1.0

        notifyItemRangeChanged(0, currencyList.size - 1, multiplier)
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

        fun bind(rateDto: RateDto) {

            val textWatcher = object : TextWatcher {
                override fun afterTextChanged(changedText: Editable?) {
                    val changedString = changedText.toString()

                    (currencyValueEditText.isFocused).let {
                        multiplier = if(changedString.isNotEmpty() && changedString != ".") {
                            changedString.toDouble()
                        } else {
                            0.0
                        }
                        textChangedCallback.onTextChanged(multiplier)
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
            }

            if (symbol != rateDto.key) {
                initView(rateDto, textWatcher)
                this.symbol = rateDto.key
            }

            // If the EditText holds the focus, we don't change the value
            if (!currencyValueEditText.isFocused) {
                currencyValueEditText.setText((rateDto.value * multiplier * multiplierForOffline).format())
            }
        }

        private fun initView(rateDto: RateDto, textWatcher: TextWatcher) {
            val currencyObj = ExtendedCurrency.getCurrencyByISO(rateDto.key)
            countryImageView.setImageResource(currencyObj.flag)

            currencyCodeTextView.text = rateDto.key
            currencyNameTextView.text = currencyObj.name

            currencyValueEditText.setText((rateDto.value * multiplier).format())
            currencyValueEditText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    //If view lost focus, we do nothing
                    if (!hasFocus) {
                        currencyValueEditText.removeTextChangedListener(textWatcher)
                        return@OnFocusChangeListener
                    }

                    layoutPosition.takeIf { it != 0 }?.also { currentPosition ->

                        swapMultipliers(currentPosition)

                        currencyList.removeAt(currentPosition).also {
                            currencyList.add(0, it)
                        }

                        itemMovedCallback.onItemMoved(currencyList[0])

                        notifyItemMoved(currentPosition, 0)
                    }

                    currencyValueEditText.addTextChangedListener(textWatcher)
                }
        }

        private fun swapMultipliers(currentPosition: Int) {
            multiplier = currencyValueEditText.text.parseToDouble()

            val itemAtCurrentPosition = currencyList[currentPosition]
            val currentItemName = itemAtCurrentPosition.key
            currencyRateMap[currentItemName]?.let {
                multiplierForOffline = 1/(it.value)
            }
        }

        private fun Double.format(): String {
            val formatter = DecimalFormat("#0.00")
            return formatter.format(this)
        }

        private fun Editable.parseToDouble(): Double {
            return this.toString().toDouble()
        }
    }
}