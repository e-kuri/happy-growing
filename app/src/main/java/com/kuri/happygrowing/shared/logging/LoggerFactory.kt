package com.kuri.happygrowing.shared.logging

import android.util.Log

private const val TAG = "HappyGrowing"

fun getLogger() = object: ILogger{
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