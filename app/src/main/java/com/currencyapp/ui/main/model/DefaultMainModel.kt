package com.currencyapp.ui.main.model

import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.Mapper
import io.reactivex.Single

const val BASE_MULTIPLIER = 1.0

class DefaultMainModel(
    private val currencyApi: CurrencyApi,
    private val mapper: Mapper<Map.Entry<String, Double>, RateDto>
) : MainModel {

    override fun retrieveCurrencyResponse(currency: String): Single<ArrayList<RateDto>> {
        return currencyApi.getCurrencies(currency)
            .map { response ->
                val list = ArrayList<RateDto>()
                list.add(
                    RateDto(
                        currency,
                        BASE_MULTIPLIER
                    )
                )

                response.rates.iterator().forEach {
                    list.add(mapper.map(it))
                }

                list
            }
    }

}