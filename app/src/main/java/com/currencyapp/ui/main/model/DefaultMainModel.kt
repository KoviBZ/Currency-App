package com.currencyapp.ui.main.model

import com.currencyapp.localrepo.room.LocalDatabase
import com.currencyapp.localrepo.room.CurrencyItemRoomDto
import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.Constants
import com.currencyapp.utils.mapper.Mapper
import io.reactivex.Completable
import io.reactivex.Single

class DefaultMainModel(
    private val currencyApi: CurrencyApi,
    private val localDatabase: LocalDatabase,
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

    override fun saveDataForOfflineMode(list: List<RateDto>): Completable {
        return Completable.fromAction {

            val localMapper = object : Mapper<RateDto, CurrencyItemRoomDto> {
                override fun map(from: RateDto): CurrencyItemRoomDto {
                    return CurrencyItemRoomDto(
                        name = from.key,
                        value = from.value
                    )
                }
            }

            list.forEach { item ->
                //CHANGE TO UPDATE
                localDatabase.itemDao.insert(localMapper.map(item))
            }
        }
    }

    override fun getOfflineData(): Single<List<RateDto>> {
        return localDatabase.itemDao.getCurrencyItems()
            .map { list ->
                val localMapper = object : Mapper<CurrencyItemRoomDto, RateDto> {
                    override fun map(from: CurrencyItemRoomDto): RateDto {
                        return RateDto(
                            key = from.name,
                            value = from.value
                        )
                    }
                }

                val finalList = ArrayList<RateDto>()

                list.forEach { item ->
                    finalList.add(localMapper.map(item))
                }

                baseCurrency = finalList[0].key

                finalList
            }
    }

}