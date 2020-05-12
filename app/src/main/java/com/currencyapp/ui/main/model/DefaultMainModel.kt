package com.currencyapp.ui.main.model

import com.currencyapp.localdb.repo.LocalRepository
import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.Constants
import com.currencyapp.utils.mapper.Mapper
import io.reactivex.Completable
import io.reactivex.Single

class DefaultMainModel(
    private val currencyApi: CurrencyApi,
    private val localRepository: LocalRepository,
    private val mapper: Mapper<Map.Entry<String, Double>, RateDto>
) : MainModel {

    private var baseCurrency: String = ""

    override fun getBaseCurrency(): String = baseCurrency

    override fun retrieveCurrencyResponse(currency: String): Single<List<RateDto>> {
        baseCurrency = currency

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

    override fun saveDataForOfflineMode(listToStore: List<RateDto>): Completable {
        return localRepository.insertData(listToStore)
    }

    override fun getOfflineData(): Single<List<RateDto>> {
        return localRepository.getOfflineData().map { list ->
                list[0].let {
                    baseCurrency = it.key
                }
                list
            }
    }

}