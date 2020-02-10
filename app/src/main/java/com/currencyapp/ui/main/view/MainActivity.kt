package com.currencyapp.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.currencyapp.R
import com.currencyapp.ui.main.view.adapter.CurrencyAdapter
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.TextChangedCallback
import com.currencyapp.ui.app.CurrencyApplication
import com.currencyapp.ui.main.di.MainModule
import com.currencyapp.ui.main.presenter.MainPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView, TextChangedCallback {

    @Inject
    lateinit var presenter: MainPresenter

    private val errorContainer: LinearLayout by lazy { findViewById<LinearLayout>(R.id.error_box_container) }
    private val errorButton: Button by lazy { findViewById<Button>(R.id.error_button) }
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
            this
        )
        recyclerView.adapter = this.adapter

        errorButton.setOnClickListener {
            presenter.retry()
            it.isEnabled = false
        }

        presenter.attachView(this)
        presenter.retrieveCurrencyResponse("EUR") //TODO magic number
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

    //TODO really!
    override fun onDataLoadedSuccess(currencyList: List<RateDto>) {
        runOnUiThread {
            errorContainer.visibility = View.GONE

            (recyclerView.adapter as CurrencyAdapter).setItemsList(currencyList)
            Log.d("MainActivity", "succeed")
        }
    }

    //TODO really!
    override fun onDataLoadedFailure(error: Throwable) {
        runOnUiThread {
            errorButton.isEnabled = true

            errorContainer.visibility = View.VISIBLE
        }
    }

    //TODO really!
    override fun showProgress() {
        runOnUiThread {
            progressBar.visibility = View.VISIBLE
            Log.d("ProgressBar", "visible")
        }
    }

    //TODO really!
    override fun hideProgress() {
        runOnUiThread {
            progressBar.visibility = View.GONE
            Log.d("ProgressBar", "gone")
        }
    }

    override fun onTextChanged(currency: String, changedMultiplier: Double) {
        presenter.onTextChanged(currency, changedMultiplier)
    }

    override fun updateRates(changedMultiplier: Double) {
        this.adapter.updateRates(changedMultiplier)
    }
}
