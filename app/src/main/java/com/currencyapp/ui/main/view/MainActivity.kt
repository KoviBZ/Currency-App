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
import com.currencyapp.ui.main.viewmodel.MainViewModel
import com.currencyapp.ui.main.view.adapter.CurrencyAdapter
import com.currencyapp.utils.callback.ItemMovedCallback
import com.currencyapp.utils.callback.OfflineCallback
import com.currencyapp.utils.callback.TextChangedCallback
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    MainView,
    TextChangedCallback,
    ItemMovedCallback,
    OfflineCallback
{

    @Inject
    lateinit var viewModel: MainViewModel

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
            this,
             this
        )
        recyclerView.adapter = this.adapter

        errorButton.setOnClickListener {
            viewModel.retry()
            it.isEnabled = false
        }

        viewModel.attachView(this)
        viewModel.retrieveCurrencyResponse()
    }

    override fun onStart() {
        super.onStart()
        viewModel.restartSubscription()
    }

    override fun onStop() {
        viewModel.clearSubscriptions()
        super.onStop()
    }

    override fun onDestroy() {
        viewModel.detachView()
        super.onDestroy()
    }

    override fun onDataLoadedSuccess(currencyList: List<RateDto>) {
        errorContainer.visibility = View.GONE
        (recyclerView.adapter as CurrencyAdapter).setItemsList(currencyList as ArrayList<RateDto>)
    }

    override fun onOfflineDataLoadedSuccess(currencyList: List<RateDto>) {
        (recyclerView.adapter as CurrencyAdapter).apply {
            setItemsList(currencyList as ArrayList<RateDto>)
            setMultiplierForOffline(currencyList[0].value)
        }
    }

    override fun onDataLoadedFailure(error: Throwable) {
        (recyclerView.adapter as CurrencyAdapter).manageOfflineData()
        errorButton.isEnabled = true
        errorContainer.visibility = View.VISIBLE
    }

    override fun updateRates(changedMultiplier: Double) {
        this.adapter.updateRates(changedMultiplier)
    }

    override fun onTextChanged(changedMultiplier: Double) {
        viewModel.onTextChanged(changedMultiplier)
    }

    override fun onItemMoved(itemOnTop: RateDto) {
        viewModel.onItemMoved(itemOnTop)
    }

    override fun saveDataForOfflineMode(list: List<RateDto>) {
        viewModel.saveDataForOfflineMode(list)
    }

    override fun getOfflineData() {
        viewModel.getOfflineData()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}
