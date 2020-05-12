package com.currencyapp.localdb.repo

import com.currencyapp.network.entity.RateDto
import io.reactivex.Completable
import io.reactivex.Single

interface LocalRepository {

    fun insertData(listToStore: List<RateDto>): Completable

    fun getOfflineData(): Single<List<RateDto>>
}