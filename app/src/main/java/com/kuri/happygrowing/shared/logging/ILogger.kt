package com.kuri.happygrowing.shared.logging

interface ILogger {
    fun logError(msg: String)

    fun logDebug(msg: String)

    fun logInfo(msg: String)
}