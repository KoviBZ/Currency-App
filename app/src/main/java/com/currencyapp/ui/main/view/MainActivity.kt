package com.currencyapp.ui.main.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.currencyapp.R
import com.currencyapp.localrepo.CurrencyAdapter
import com.currencyapp.localrepo.RateDto
import com.currencyapp.network.di.NetworkModule
import com.currencyapp.ui.app.CurrencyApplication
import com.currencyapp.ui.main.di.MainComponent
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.presenter.MainPresenter
import com.currencyapp.utils.mapper.RatesToRateDtosMapper


class MainActivity : AppCompatActivity(), MainView {

//    @Inject
    lateinit var presenter: MainPresenter

    val PREFS_FILENAME = "com.teamtreehouse.colorsarefun.prefs"

    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        CurrencyApplication.getApplicationComponent().inject(this)

        val prefs = getSharedPreferences(PREFS_FILENAME, 0)

        val model = MainModel(NetworkModule.provideCurrencyApi(), prefs)

        presenter = MainPresenter(model, RatesToRateDtosMapper())
        presenter.attachView(this)
        presenter.retrieveCurrencyResponse("EUR")
    }

//    override fun onStart() {
//        super.onStart()
//        presenter.restartSubscription()
//    }

    override fun onStop() {
        presenter.clearSubscriptions()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.disposeSubscriptions()
        super.onDestroy()
    }

    override fun onDataLoadedSuccess(currencyList: ArrayList<RateDto>) {
        recyclerView.adapter = CurrencyAdapter(
            this,
            currencyList,
            object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    presenter.onTextChanged(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

            }
        )
    }

    override fun onDataLoadedFailure(error: Throwable) {
        Toast.makeText(applicationContext, "failed...", Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        Log.d("ProgressBar", "visible")
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        Log.d("ProgressBar", "gone")
    }

    override fun onCashFieldClicked() {

    }

    override fun onCashFieldChanged() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
