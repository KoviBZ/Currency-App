package com.currencyapp.ui.main.model

import com.currencyapp.localdb.repo.LocalRepository
import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.repo.RemoteRepository
import io.reactivex.Completable
import io.reactivex.Single

class DefaultMainModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : MainModel {

    private var baseCurrency: String = ""

    override fun getBaseCurrency(): String = baseCurrency

    override fun retrieveCurrencyResponse(currency: String): Single<List<RateDto>> {
        baseCurrency = currency
        return remoteRepository.getCurrencyResponse(baseCurrency)
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