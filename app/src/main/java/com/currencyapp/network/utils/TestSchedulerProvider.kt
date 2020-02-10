package com.currencyapp.network.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider : BaseSchedulerProvider {

    override fun io(): Scheduler {
        return TestScheduler()
    }

    override fun ui(): Scheduler {
        return TestScheduler()
    }

}