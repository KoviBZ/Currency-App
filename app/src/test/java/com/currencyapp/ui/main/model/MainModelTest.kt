package com.currencyapp.ui.main.model

import com.currencyapp.localrepo.room.CurrencyItemRoomDto
import com.currencyapp.localrepo.room.LocalDatabase
import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.CurrencyResponse
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.Mapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.ArgumentMatchers.anyString
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class MainModelTest : Spek({

    val api: CurrencyApi by memoized { mock<CurrencyApi>() }
    val localDatabase: LocalDatabase by memoized { mock<LocalDatabase>() }
    val mapper: Mapper<Map.Entry<String, Double>, RateDto> by memoized { mock<Mapper<Map.Entry<String, Double>, RateDto>>() }
    val databaseMapper: Mapper<CurrencyItemRoomDto, RateDto> by memoized { mock<Mapper<CurrencyItemRoomDto, RateDto>>() }

    val model: MainModel by memoized { DefaultMainModel(api, localDatabase, mapper, databaseMapper) }

    describe("retrieve currency response") {

        lateinit var observer: TestObserver<List<RateDto>>
        val requestCurrency = "EUR"
        val apiResponse = apiResponse()

        beforeEachTest {
            observer = TestObserver<List<RateDto>>()

            given(api.getCurrencies(anyString())).willReturn(Single.just(apiResponse))
            given(mapper.map(any())).willReturn(RateDto("example", 21.21))

            model.retrieveCurrencyResponse(requestCurrency).subscribe(observer)
        }

        it("should complete") {
            observer.assertComplete()
        }

        it("should return amount of items from map + 1 default") {
            assertEquals(observer.values()[0].size, apiResponse.rates.size + 1)
        }

        it("should return currency from request as first") {
            assertEquals(observer.values()[0][0].key, requestCurrency)
        }
    }

    describe("save offline data") {

        val observer = TestObserver<List<RateDto>>()
        val listToSave = listOf(RateDto("example", 21.21))

        beforeEachTest {
            given(localDatabase.itemDao).willReturn(mock())
            given(mapper.map(any())).willReturn(RateDto("example", 21.21))

            model.saveDataForOfflineMode(listToSave).subscribe(observer)
        }

        it("should complete") {
            observer.assertComplete()
        }
    }

    describe("get offline data") {

        lateinit var observer : TestObserver<List<RateDto>>
        val databaseResponse = listOf(CurrencyItemRoomDto("example", 21.21))

        beforeEachTest {
            observer = TestObserver<List<RateDto>>()

            given(localDatabase.itemDao).willReturn(mock())
            given(localDatabase.itemDao.getCurrencyItems()).willReturn(Single.just(databaseResponse))
            given(databaseMapper.map(any())).willReturn(RateDto("example", 21.21))

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

private fun apiResponse(): CurrencyResponse {
    val ratesMap = LinkedHashMap<String, Double>()
    ratesMap["EUR"] = 1.0
    ratesMap["PLN"] = 4.32

    return CurrencyResponse(
        "USD",
        ratesMap
    )
}