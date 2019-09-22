package com.currencyapp.ui.main.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.currencyapp.R
import com.currencyapp.dto.CurrencyAdapter
import com.currencyapp.dto.CurrencyResponse
import com.currencyapp.network.di.NetworkModule
import com.currencyapp.ui.app.CurrencyApplication
import com.currencyapp.ui.main.model.MainModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler


class MainActivity : AppCompatActivity(), MainView {

//    @Inject
//    lateinit var presenter: MainPresenter

    val model = MainModel(NetworkModule.provideCurrencyApi())

    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        CurrencyApplication.getApplicationComponent().inject(this)

        model.retrieveCurrencyResponse("EUR")
            .subscribeOn(IoScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { response ->
                recyclerView.adapter = CurrencyAdapter(this, response.rates)
            }
            .doOnError {
                Log.e("errorinmodel", it.message)
            }
            .subscribe()
    }

    override fun onDestroy() {
//        presenter.detachView()
        super.onDestroy()
    }

    override fun onDataLoadedSuccess(currencyList: List<CurrencyResponse>) {
        Toast.makeText(applicationContext, "worked!", Toast.LENGTH_SHORT).show()
    }

    override fun onDataLoadedFailure(error: Throwable) {
        Toast.makeText(applicationContext, "failed...", Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCashFieldClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCashFieldChanged() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun providePresenter() {

    }
}
