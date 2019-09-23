package com.currencyapp.localrepo

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.currencyapp.R
import com.currencyapp.utils.CountryConverter
import java.util.*

class CurrencyAdapter(
    private val context: Context,
    private val itemsList: ArrayList<RateDto>,
    private val textWatcher: TextWatcher
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

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

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(
            itemsList[position],
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    Collections.swap(itemsList, 0, position) //TODO check positions
                    notifyItemMoved(0, position)
                }
            },
            textWatcher)
    }

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val countryImageView by lazy { view.findViewById<ImageView>(R.id.country_iv) }
        private val currencyValueEditText by lazy { view.findViewById<EditText>(R.id.currency_value_et) }

        fun bind(rateDto: RateDto, focusListener: View.OnFocusChangeListener, textWatcher: TextWatcher) {
            val imageRes = CountryConverter.getImageForCountry(rateDto.key)
            countryImageView.setImageResource(imageRes)

            currencyValueEditText.setText(rateDto.value.toString())
            currencyValueEditText.onFocusChangeListener = focusListener
            currencyValueEditText.addTextChangedListener(textWatcher)
        }
    }
}