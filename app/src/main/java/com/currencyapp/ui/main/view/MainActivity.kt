package com.currencyapp.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.currencyapp.R
import com.currencyapp.network.entity.RateDto
import com.currencyapp.ui.app.CurrencyApplication
import com.currencyapp.ui.main.di.MainModule
import com.currencyapp.ui.main.presenter.MainPresenter
import com.currencyapp.ui.main.view.adapter.CurrencyAdapter
import com.currencyapp.utils.Constants
import com.currencyapp.utils.ItemMovedCallback
import com.currencyapp.utils.TextChangedCallback
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView, TextChangedCallback, ItemMovedCallback {

    @Inject
    lateinit var presenter: MainPresenter

    private val errorContainer: LinearLayout by lazy { findViewById<LinearLayout>(R.id.retry_container) }
    private val errorButton: Button by lazy { findViewById<Button>(R.id.retry_button) }
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }

    private lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appComponent = CurrencyApplication.getApplicationComponent()
        val mainComponent = appComponent.plusMainComponent(MainModule())
        mainComponent.inject(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        this.adapter = CurrencyAdapter(
            applicationContext,
            this,
            this
        )
        recyclerView.adapter = this.adapter

        errorButton.setOnClickListener {
            presenter.retry()
            it.isEnabled = false
        }

        presenter.attachView(this)
        presenter.retrieveCurrencyResponse(Constants.DEFAULT_CURRENCY)
    }

    override fun onStart() {
        super.onStart()
        presenter.restartSubscription()
    }

    override fun onStop() {
        presenter.clearSubscriptions()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.disposeSubscriptions()
        super.onDestroy()
    }

    override fun onDataLoadedSuccess(currencyList: List<RateDto>) {
        errorContainer.visibility = View.GONE
        (recyclerView.adapter as CurrencyAdapter).setItemsList(currencyList as ArrayList<RateDto>)
    }

    override fun onDataLoadedFailure(error: Throwable) {
        errorButton.isEnabled = true
        errorContainer.visibility = View.VISIBLE
    }

    override fun updateRates(changedMultiplier: Double) {
        this.adapter.updateRates(changedMultiplier)
    }

    override fun onTextChanged(changedMultiplier: Double) {
        presenter.onTextChanged(changedMultiplier)
    }

    override fun onItemMoved(itemOnTop: RateDto) {
        presenter.onItemMoved(itemOnTop)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}
