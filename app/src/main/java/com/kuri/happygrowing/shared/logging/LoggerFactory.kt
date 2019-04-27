package com.kuri.happygrowing.shared.logging

import android.util.Log
import com.kuri.happygrowing.stats.repository.measurement.StatsRepositoryFactory

fun getLogger() = object: ILogger{
    override fun logError(msg: String) {
        Log.e(StatsRepositoryFactory.TAG, msg)
    }

    override fun logDebug(msg: String) {
        Log.d(StatsRepositoryFactory.TAG, msg)
    }

    override fun logInfo(msg: String) {
        Log.i(StatsRepositoryFactory.TAG, msg)
    }
}