package com.kuri.happygrowing.shared.logging

import android.util.Log
import java.lang.Exception

private const val TAG = "HappyGrowing"

fun getLogger() = object: ILogger{
    override fun logError(e: Exception) {
        Log.e(TAG, e.message)
    }

    override fun logError(msg: String) {
        Log.e(TAG, msg)
    }

    override fun logDebug(msg: String) {
        Log.d(TAG, msg)
    }

    override fun logInfo(msg: String) {
        Log.i(TAG, msg)
    }
}