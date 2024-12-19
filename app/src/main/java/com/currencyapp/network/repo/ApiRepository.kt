package com.currencyapp.network.repo

import com.currencyapp.extensions.handleResponse
import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.Constants
import com.currencyapp.utils.mapper.Mapper

class ApiRepository(
    private val api: CurrencyApi,
    private val mapper: Mapper<Map.Entry<String, Double>, RateDto>
) : RemoteRepository {

    private var baseCurrency: String = ""

    override fun getBaseCurrency(): String = baseCurrency

    override fun getCurrencyResponse(currencyName: String): Resource<List<RateDto>> {
        baseCurrency = currencyName

        return api.getCurrencies(currencyName).handleResponse(
            onSuccess = { response ->
                val list = ArrayList<RateDto>()

                list.add(RateDto(currencyName, Constants.DEFAULT_MULTIPLIER))

                response.rates.iterator().forEach {
                    list.add(mapper.map(it))
                }

                list
            }
        )
    }
}