package com.currencyapp.dto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.currencyapp.R
import com.currencyapp.utils.CountryConverter

class CurrencyAdapter(
    private val context: Context,
    private val items: List<RateDto>
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

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val countryImageView by lazy { view.findViewById<ImageView>(R.id.country_iv) }
        private val currencyValueEditText by lazy { view.findViewById<EditText>(R.id.currency_value_et) }

        fun bind(rateDto: RateDto) {
            val imageRes = CountryConverter.getImageForCountry(rateDto.code)
            countryImageView.setImageResource(imageRes)

            currencyValueEditText.setText("asd")
        }
    }
}