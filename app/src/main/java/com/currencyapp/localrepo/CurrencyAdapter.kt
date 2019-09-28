package com.currencyapp.localrepo

import android.content.Context
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.currencyapp.R
import com.currencyapp.utils.CountryConverter
import java.text.DecimalFormat
import java.util.*

class CurrencyAdapter(
    private val context: Context,
    private val itemsList: ArrayList<RateDto>,
    private val onClickListener: OnItemClickListener,
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
            onClickListener,
            View.OnClickListener {
                Collections.swap(itemsList, position, 0)
                notifyItemMoved(position, 0)
            },
            textWatcher
        )
    }

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val countryImageView by lazy { view.findViewById<ImageView>(R.id.country_iv) }
        private val countryTextView by lazy { view.findViewById<TextView>(R.id.country_tv) }
        private val currencyValueEditText by lazy { view.findViewById<EditText>(R.id.currency_value_et) }

        fun bind(
            rateDto: RateDto,
            onItemClickListener: OnItemClickListener,
            onClickListener: View.OnClickListener,
            textWatcher: TextWatcher
        ) {
            val imageRes = CountryConverter.getImageForCountry(rateDto.key)
            countryImageView.setImageResource(imageRes)

            countryTextView.text = rateDto.key

            val formatter = DecimalFormat("#.00")
            currencyValueEditText.setText(formatter.format(rateDto.value))
            currencyValueEditText.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    onItemClickListener.onItemClicked(rateDto)
                    onClickListener.onClick(v)
                    currencyValueEditText.addTextChangedListener(textWatcher)
                } else {
                    currencyValueEditText.removeTextChangedListener(textWatcher)
                }
            }
        }
    }
}