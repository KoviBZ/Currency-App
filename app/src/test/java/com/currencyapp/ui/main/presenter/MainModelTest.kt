package com.currencyapp.ui.main.presenter

import com.currencyapp.network.CurrencyApi
import com.currencyapp.ui.main.model.DefaultMainModel
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import com.currencyapp.utils.mapper.RatesToRateDtosMapper
import org.mockito.Mockito.mock
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class MainModelTest : Spek({

    val api: CurrencyApi by memoized<CurrencyApi>()
    val mapper: RatesToRateDtosMapper by memoized<RatesToRateDtosMapper>()

    val model: MainModel by memoized { DefaultMainModel(api, mapper) }
})