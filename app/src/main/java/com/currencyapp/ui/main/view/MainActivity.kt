package com.currencyapp.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.currencyapp.R
import com.currencyapp.network.entity.RateDto
import com.currencyapp.ui.main.presenter.CurrencyUiState
import com.currencyapp.ui.main.presenter.MainViewModel
import com.currencyapp.ui.main.view.adapter.CurrencyAdapter
import com.currencyapp.utils.callback.ItemMovedCallback
import com.currencyapp.utils.callback.TextChangedCallback
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: AppCompatActivity(),
    TextChangedCallback,
    ItemMovedCallback
{
    private val viewModel: MainViewModel by viewModel<MainViewModel>()

    private val errorContainer: LinearLayout by lazy { findViewById(R.id.retry_container) }
    private val errorButton: Button by lazy { findViewById(R.id.retry_button) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recycler_view) }
    private val progressBar: ProgressBar by lazy { findViewById(R.id.progress_bar) }

    private lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        this.adapter = CurrencyAdapter(
            applicationContext,
            this,
            this
        )
        recyclerView.adapter = this.adapter

        errorButton.setOnClickListener {
            viewModel.restartSubscription()
            it.isEnabled = false
        }

        lifecycleScope.launch {
            viewModel.currencyUiState.collect { state ->
                when (state) {
                    is CurrencyUiState.Loading -> progressBar.visibility = View.VISIBLE
                    is CurrencyUiState.Success -> onDataLoadedSuccess(state.currencies)
                    is CurrencyUiState.Error -> onDataLoadedFailure()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.modifierUiState.collect {
                updateRates(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.restartSubscription()
    }

    override fun onStop() {
        viewModel.clearSubscriptions()
        super.onStop()
    }

    private fun onDataLoadedSuccess(currencyList: List<RateDto>) {
        errorContainer.visibility = View.GONE
        progressBar.visibility = View.GONE
        (recyclerView.adapter as CurrencyAdapter).setItemsList(currencyList as ArrayList<RateDto>)
    }

    private fun onDataLoadedFailure() {
        viewModel.clearSubscriptions()
        errorButton.isEnabled = true
        progressBar.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
    }

    private fun updateRates(changedMultiplier: Double) {
        this.adapter.updateRates(changedMultiplier)
    }

    override fun onTextChanged(changedMultiplier: Double) {
        viewModel.onTextChanged(changedMultiplier)
    }

    override fun onItemMoved(itemOnTop: RateDto) {
        viewModel.onItemMoved(itemOnTop)
    }
}
