package com.currencyapp.ui.main.model

import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.CurrencyResponse
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.Mapper
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.mockito.ArgumentMatchers.anyString
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class MainModelTest : Spek({

    val api: CurrencyApi by memoized { mock<CurrencyApi>() }
    val mapper: Mapper<Map.Entry<String, Double>, RateDto> by memoized { mock<Mapper<Map.Entry<String, Double>, RateDto>>() }

    val model: MainModel by memoized { DefaultMainModel(api, mapper) }

    describe("retrieve currency response") {

        val observer = TestObserver<List<RateDto>>()
        val apiResponse = apiResponse()

        beforeEachTest {
            given(api.getCurrencies(anyString())).willReturn(Single.just(apiResponse))
//            given(mapper.map(any())).willReturn(mock(RateDto::class.java))

            model.retrieveCurrencyResponse(anyString()).subscribe(observer)
        }

        it("should complete") {
            observer.assertComplete()
        }
    }
})

private fun apiResponse(): CurrencyResponse {
    val ratesMap = LinkedHashMap<String, Double>()
    ratesMap["EUR"] = 1.0
    ratesMap["PLN"] = 4.32

    return CurrencyResponse(
        "base",
        "2019-08-09",
        ratesMap
    )
}