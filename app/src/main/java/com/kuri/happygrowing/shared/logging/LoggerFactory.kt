package com.kuri.happygrowing.shared.logging

import android.util.Log
import com.kuri.happygrowing.stats.repository.measurement.RepositoryFactory

fun getLogger() = object: ILogger{
    override fun logError(msg: String) {
        Log.e(RepositoryFactory.TAG, msg)
    }

    override fun logDebug(msg: String) {
        Log.d(RepositoryFactory.TAG, msg)
    }

    override fun logInfo(msg: String) {
        Log.i(RepositoryFactory.TAG, msg)
    }
}