package com.currencyapp.localdb.repo

import com.currencyapp.localdb.ItemDao
import com.currencyapp.localdb.LocalDatabase
import com.currencyapp.localdb.LocalDatabaseRateDto
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.TwoWayMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class LocalRepositoryTest : Spek({

    val localDatabase: LocalDatabase by memoized { mock<LocalDatabase>() }
    val mapper: TwoWayMapper<LocalDatabaseRateDto, RateDto> by memoized { mock<TwoWayMapper<LocalDatabaseRateDto, RateDto>>() }

    val repository: LocalRepository by memoized { LocalDatabaseRepository(localDatabase, mapper) }

    val exampleItem = RateDto("example", 21.21)

    describe("insert data") {

        val observer = TestObserver<Void>()
        val list = listOf(exampleItem)

        beforeEachTest {
            given(localDatabase.itemDao).willReturn(mock())

            repository.insertData(list).subscribe(observer)
        }

        it("should complete") {
            observer.assertComplete()
        }
    }

    describe("get offline data") {

        lateinit var observer: TestObserver<List<RateDto>>
        val list = listOf(LocalDatabaseRateDto("sth", 1.1))

        beforeEachTest {
            observer = TestObserver<List<RateDto>>()

            given(localDatabase.itemDao).willReturn(mock())
            given(localDatabase.itemDao.getCurrencyItems()).willReturn(Single.just(list))
            given(mapper.map(any())).willReturn(exampleItem)

            repository.getOfflineData().subscribe(observer)
        }

        it("should complete") {
            observer.assertComplete()
        }

        it("should return same amount of items as db response") {
            assertEquals(observer.values()[0].size, list.size)
        }
    }
})