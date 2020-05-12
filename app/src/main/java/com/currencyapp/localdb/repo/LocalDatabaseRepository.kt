package com.currencyapp.localdb.repo

import com.currencyapp.localdb.LocalDatabaseRateDto
import com.currencyapp.localdb.LocalDatabase
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.Mapper
import io.reactivex.Completable
import io.reactivex.Single

class LocalDatabaseRepository(
    private val localDatabase: LocalDatabase,
    private val mapper: Mapper<LocalDatabaseRateDto, RateDto>
): LocalRepository {

    override fun getOfflineData(): Single<List<RateDto>> {
        return localDatabase.itemDao.getCurrencyItems()
            .map { list ->

                val finalList = ArrayList<RateDto>()

                list.forEach { item ->
                    finalList.add(mapper.map(item))
                }

                finalList
            }
    }

    override fun insertData(listToStore: List<RateDto>): Completable {
        return Completable.fromAction {

            val localMapper = object : Mapper<RateDto, LocalDatabaseRateDto> {
                override fun map(from: RateDto): LocalDatabaseRateDto {
                    return LocalDatabaseRateDto(
                        name = from.key,
                        value = from.value
                    )
                }
            }

            listToStore.forEach { item ->
                localDatabase.itemDao.insert(localMapper.map(item))
            }
        }
    }
}