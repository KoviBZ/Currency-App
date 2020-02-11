package com.currencyapp.ui.main.presenter

import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.network.utils.TestSchedulerProvider
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import io.reactivex.Single
import io.reactivex.internal.schedulers.TrampolineScheduler
import io.reactivex.schedulers.TestScheduler
import org.mockito.BDDMockito.given
import org.mockito.Matchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.concurrent.TimeUnit

const val TEST_CURRENCY = "test"

class MainPresenterTest : Spek({

    val view: MainView by memoized { mock(MainView::class.java) }

    val schedulers: BaseSchedulerProvider by memoized { TestSchedulerProvider() }
    val model: MainModel by memoized { mock(MainModel::class.java) }

    val presenter by memoized { MainPresenter(schedulers, model) }

    beforeEachTest {
        presenter.attachView(view)
    }

    describe("attach view") {

        beforeEachTest {
            (schedulers.io() as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)
            (schedulers.ui() as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)
        }

        context("request succeeds") {

            val list = ArrayList<RateDto>()

            beforeEachTest {
                given(model.retrieveCurrencyResponse(anyString())).willReturn(
                    Single.just(
                        list
                    )
                )

                presenter.retrieveCurrencyResponse(TEST_CURRENCY)
            }

            it("should call on data loaded success") {
                verify(view).onDataLoadedSuccess(list)
            }
        }

        context("request fails") {

            val errorResponse = Throwable()

            beforeEachTest {
                given(model.retrieveCurrencyResponse(anyString())).willReturn(
                    Single.error(
                        errorResponse
                    )
                )

                presenter.retrieveCurrencyResponse(TEST_CURRENCY)
            }

            it("should call on data loaded failure") {
                verify(view).onDataLoadedFailure(errorResponse)
            }
        }
    }
})