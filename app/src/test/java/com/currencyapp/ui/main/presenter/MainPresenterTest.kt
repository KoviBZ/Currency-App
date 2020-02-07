package com.currencyapp.ui.main.presenter

import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.network.utils.TestSchedulerProvider
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import io.reactivex.Single
import org.mockito.BDDMockito.given
import org.mockito.Matchers.anyString
import org.mockito.Mockito.mock
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

const val TEST_CURRENCY = "test"

class MainPresenterTest : Spek({

    val view: MainView by memoized { mock(MainView::class.java) }

    val schedulers: BaseSchedulerProvider by memoized { TestSchedulerProvider() }
    val model: MainModel by memoized { mock(MainModel::class.java) }

    val presenter by memoized { MainPresenter(schedulers, model) }

    describe("attach view") {

        beforeEachTest {
            presenter.attachView(view)
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
                view.onDataLoadedSuccess(list)
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
                view.onDataLoadedFailure(errorResponse)
            }
        }
    }
})