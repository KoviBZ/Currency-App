package com.currencyapp.ui.main.model

import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.Constants
import com.currencyapp.utils.mapper.Mapper
import io.reactivex.Single

class DefaultMainModel(
    private val currencyApi: CurrencyApi,
    private val mapper: Mapper<Map.Entry<String, Double>, RateDto>
) : MainModel {

    private var baseCurrency: String = ""

    override fun setBaseCurrency(currency: String) {
        this.baseCurrency = currency
    }

    override fun getBaseCurrency(): String = baseCurrency

    override fun retrieveCurrencyResponse(currency: String): Single<List<RateDto>> {
        return currencyApi.getCurrencies(currency)
            .map { response ->
                val list = ArrayList<RateDto>()
                list.add(
                    RateDto(
                        currency,
                        Constants.DEFAULT_MULTIPLIER
                    )
                )

                response.rates.iterator().forEach {
                    list.add(mapper.map(it))
                }

                list
            }
    }

}