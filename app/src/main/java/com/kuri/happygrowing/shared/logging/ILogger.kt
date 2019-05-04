package com.kuri.happygrowing.shared.logging

import java.lang.Exception

interface ILogger {
    fun logError(msg: String)

    fun logError(e: Exception)

    fun logDebug(msg: String)

    fun logInfo(msg: String)
}