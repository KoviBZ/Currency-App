package com.currencyapp.ui.main.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.currencyapp.R
import com.currencyapp.dto.CurrencyAdapter
import com.currencyapp.dto.CurrencyResponse
import com.currencyapp.dto.RateDto
import com.currencyapp.ui.app.CurrencyApplication
import com.currencyapp.ui.main.di.MainComponent
import com.currencyapp.ui.main.presenter.MainPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

//    @Inject
//    lateinit var presenter: MainPresenter

    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CurrencyApplication.getApplicationComponent().inject(this)

        val rate1 = RateDto("EUR", 1.0)
        val rate2 = RateDto("USD", 1.4)
        val rate3 = RateDto("GBP", 0.87)
        val rate4 = RateDto("PLN", 4.0)

        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter = CurrencyAdapter(this, listOf(rate1, rate2, rate3, rate4))
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
