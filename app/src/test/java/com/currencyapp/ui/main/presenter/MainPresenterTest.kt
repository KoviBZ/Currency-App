package com.currencyapp.ui.main.presenter

import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.network.utils.TestSchedulerProvider
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Single
import org.mockito.ArgumentMatchers.anyString
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

const val TEST_CURRENCY = "GBP"
const val TEST_MULTIPLIER = 13.8

class MainPresenterTest : Spek({

    val view: MainView by memoized { mock<MainView>() }

    val schedulers: BaseSchedulerProvider by memoized { TestSchedulerProvider() }
    val model: MainModel by memoized { mock<MainModel>() }

    val presenter by memoized { MainPresenter(schedulers, model) }

    val testItem by memoized { RateDto(TEST_CURRENCY, TEST_MULTIPLIER) }
    val response: List<RateDto> by memoized { emptyList<RateDto>() }

    beforeEachTest {
        presenter.attachView(view)
    }

    describe("attach view") {

        context("request returns success") {

            beforeEachTest {
                given(model.retrieveCurrencyResponse(anyString())).willReturn(
                    Single.just(response)
                )

                presenter.retrieveCurrencyResponse()
            }

            it("should call on data loaded success") {
                verify(view).onDataLoadedSuccess(response)
            }

            it("should call on data loaded success") {
                verify(view).onDataLoadedSuccess(response)
            }

            it("should call on data loaded success") {
                assert(presenter.subscriptions.size() != 0)
            }

        }

        context("request returns error") {

            val errorResponse = Throwable()

            beforeEachTest {
                given(model.retrieveCurrencyResponse(anyString())).willReturn(
                    Single.error(errorResponse)
                )

                presenter.retrieveCurrencyResponse(TEST_CURRENCY)
            }

            it("should call on data loaded failure") {
                verify(view).onDataLoadedFailure(errorResponse)
            }
        }
    }

    describe("on text changed") {

        beforeEachTest {
            presenter.onTextChanged(TEST_MULTIPLIER)

            it("should call update rates") {
                verify(view).updateRates(TEST_MULTIPLIER)
            }
        }
    }

    describe("on item moved") {

        context("and base currency didn't change") {
            beforeEachTest {
                given(model.retrieveCurrencyResponse(anyString())).willReturn(
                    Single.just(response)
                )

                presenter.onItemMoved(testItem)
            }

            it("should call on data loaded success") {
                verify(view).onDataLoadedSuccess(response)
            }
        }
    }

    describe("restart subscription") {

        context("subscriptions are empty") {
            beforeEachTest {
                given(model.retrieveCurrencyResponse(anyString())).willReturn(
                    Single.just(response)
                )
                given(model.getBaseCurrency()).willReturn(TEST_CURRENCY)

                presenter.restartSubscription()
            }

            it("should call on data loaded success") {
                verify(view).onDataLoadedSuccess(response)
            }
        }

        context("subscriptions are not empty") {
            beforeEachTest {
                given(model.retrieveCurrencyResponse(anyString())).willReturn(
                    Single.just(response)
                )
                given(model.getBaseCurrency()).willReturn(TEST_CURRENCY)
                presenter.subscriptions.add(mock())

                presenter.restartSubscription()
            }

            it("should not interact with view") {
                verifyNoMoreInteractions(view)
            }
        }
    }

    describe("retry") {

        beforeEachTest {
            given(model.retrieveCurrencyResponse(anyString())).willReturn(
                Single.just(response)
            )
            given(model.getBaseCurrency()).willReturn(TEST_CURRENCY)

            presenter.retry()
        }

        it("should call on data loaded success") {
            verify(view).onDataLoadedSuccess(response)
        }

        it("should call show progress") {
            verify(view).showProgress()
        }

        it("should call hide progress") {
            verify(view).hideProgress()
        }
    }
})