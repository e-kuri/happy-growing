package com.kuri.happygrowing.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.kuri.happygrowing.settings.repository.ISettingsRepository
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.shared.logging.ILogger

class SettingsViewModel(private val repo: ISettingsRepository, private val logger: ILogger) : ViewModel() {

    private val updateListener = object: OnResultCallback<Boolean> {
        override fun onSuccessResult(result: Boolean) {

        }

        override fun onError(e: Exception) {
            logger.logError(e)
        }
    }

    fun setFloatPreference(key: String, value: String) =
        try {
            val parsed = value.toFloat()
            repo.setValue(key, parsed, updateListener)
            true
        } catch (e: NumberFormatException) {
            false
        }

}