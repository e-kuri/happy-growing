package com.kuri.happygrowing.shared

import com.kuri.happygrowing.shared.logging.ILogger
import java.lang.Exception

class TestLogger : ILogger{
    override fun logError(msg: String) {
        println("ERROR: $msg")
    }

    override fun logError(e: Exception) {
        logError(e.message ?: "")
    }

    override fun logDebug(msg: String) {
        println("DEBUG: $msg")
    }

    override fun logInfo(msg: String) {
        println("INFO: $msg")
    }
}