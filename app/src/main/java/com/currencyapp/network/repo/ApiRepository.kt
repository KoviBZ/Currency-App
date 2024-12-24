package com.currencyapp.network.repo

import android.util.Log
import com.currencyapp.extensions.handleResponse
import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.Constants
import com.currencyapp.utils.mapper.Mapper
import kotlin.random.Random

class ApiRepository(
    val api: CurrencyApi,
    val mapper: Mapper<Map.Entry<String, Double>, RateDto>
) : RemoteRepository {

    private var baseCurrency: String = ""

    override fun getBaseCurrency(): String = baseCurrency

    override suspend fun getCurrencyResponse(currencyName: String): Resource<List<RateDto>> {
        baseCurrency = currencyName

        return api.getCurrencies(currencyName).handleResponse(
            onSuccess = { response ->
                val list = ArrayList<RateDto>()

                list.add(RateDto(currencyName, Constants.DEFAULT_MULTIPLIER))

                response.rates.iterator().forEach {
                    list.add(mapper.map(it))
                }

                list
            },
            onError = {
                Log.e("testo", "job failed so mocking.\n")
                Resource.Success(tmpMock())
            }
        )
    }

    fun tmpMock(): List<RateDto> {
        val list = ArrayList<RateDto>()

        list.add(RateDto("PLN", 1.0))
        list.add(RateDto("USD", Random.nextDouble(4.0, 4.2)))
        list.add(RateDto("EUR", Random.nextDouble(4.16, 4.36)))
        list.add(RateDto("GBP", Random.nextDouble(5.04, 5.24)))
        list.add(RateDto("CHF", Random.nextDouble(4.45, 4.65)))
        list.add(RateDto("UAH", Random.nextDouble(0.088, 0.108)))
        list.add(RateDto("RUB", Random.nextDouble(0.031, 0.051)))

        return list
    }
}