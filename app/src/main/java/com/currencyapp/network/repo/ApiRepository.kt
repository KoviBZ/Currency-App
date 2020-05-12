package com.currencyapp.network.repo

import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.Constants
import com.currencyapp.utils.mapper.Mapper
import io.reactivex.Single

class ApiRepository(
    private val api: CurrencyApi,
    private val mapper: Mapper<Map.Entry<String, Double>, RateDto>
) : RemoteRepository {

    override fun getCurrencyResponse(currencyName: String): Single<List<RateDto>> {
        return api.getCurrencies(currencyName).map { response ->
            val list = ArrayList<RateDto>()

            list.add(RateDto(currencyName, Constants.DEFAULT_MULTIPLIER))

            response.rates.iterator().forEach {
                list.add(mapper.map(it))
            }

            list
        }
    }
}