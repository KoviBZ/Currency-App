package com.currencyapp.ui.main.model

import com.currencyapp.localdb.repo.LocalRepository
import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.repo.RemoteRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.ArgumentMatchers.anyString
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class MainModelTest : Spek({

    val remoteRepository: RemoteRepository by memoized { mock<RemoteRepository>() }
    val localRepository: LocalRepository by memoized { mock<LocalRepository>() }

    val model: MainModel by memoized { DefaultMainModel(remoteRepository, localRepository) }

    describe("retrieve currency response") {

        lateinit var observer: TestObserver<List<RateDto>>
        val requestCurrency = "EUR"
        val apiResponse = listOf(RateDto("example", 21.21))

        beforeEachTest {
            observer = TestObserver<List<RateDto>>()

            given(remoteRepository.getCurrencyResponse(anyString())).willReturn(Single.just(apiResponse))

            model.retrieveCurrencyResponse(requestCurrency).subscribe(observer)
        }

        it("should complete") {
            observer.assertComplete()
        }

        it("should return amount of items from map + 1 default") {
            assertEquals(observer.values()[0].size, apiResponse.size)
        }
    }

    describe("save offline data") {

        val observer = TestObserver<List<RateDto>>()
        val listToSave = listOf(RateDto("example", 21.21))

        beforeEachTest {
            given(localRepository.insertData(any())).willReturn(Completable.complete())

            model.saveDataForOfflineMode(listToSave).subscribe(observer)
        }

        it("should complete") {
            observer.assertComplete()
        }
    }

    describe("get offline data") {

        lateinit var observer : TestObserver<List<RateDto>>
        val databaseResponse = listOf(RateDto("example", 21.21))

        beforeEachTest {
            observer = TestObserver<List<RateDto>>()

            given(localRepository.getOfflineData()).willReturn(Single.just(databaseResponse))

            model.getOfflineData().subscribe(observer)
        }

        it("should complete") {
            observer.assertComplete()
        }

        it("should have same amount of items as response has entities") {
            observer.assertValueCount(databaseResponse.size)
        }
    }
})