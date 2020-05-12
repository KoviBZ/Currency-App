package com.currencyapp.localdb.repo

import com.currencyapp.localdb.LocalDatabaseRateDto
import com.currencyapp.localdb.LocalDatabase
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.TwoWayMapper
import io.reactivex.Completable
import io.reactivex.Single

class LocalDatabaseRepository(
    private val localDatabase: LocalDatabase,
    private val localDatabaseMapper: TwoWayMapper<LocalDatabaseRateDto, RateDto>
): LocalRepository {

    override fun getOfflineData(): Single<List<RateDto>> {
        return localDatabase.itemDao.getCurrencyItems()
            .map { list ->
                val finalList = ArrayList<RateDto>()

                list.forEach { item ->
                    finalList.add(localDatabaseMapper.map(item))
                }

                finalList
            }
    }

    override fun insertData(listToStore: List<RateDto>): Completable {
        return Completable.fromAction {
            listToStore.forEach { item ->
                localDatabase.itemDao.insert(localDatabaseMapper.mapRevert(item))
            }
        }
    }
}