package com.kuri.happygrowing.settings.repository

import androidx.preference.PreferenceDataStore
import com.kuri.happygrowing.settings.model.Settings
import com.kuri.happygrowing.shared.SETTINGS_MAX_HUM_KEY
import com.kuri.happygrowing.shared.SETTINGS_MAX_TEMP_KEY
import com.kuri.happygrowing.shared.SETTINGS_MIN_HUM_KEY
import com.kuri.happygrowing.shared.SETTINGS_MIN_TEMP_KEY
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.shared.logging.ILogger

class HGPreferenceDataStore(val repo: ISettingsRepository, val logger: ILogger): PreferenceDataStore() {

    private lateinit var settings: Settings

    private val onSettingsRetrievedCallback = object: OnResultCallback<Settings>{
        override fun onSuccessResult(result: Settings) {
            settings = result
        }

        override fun onError(e: Exception) {
            logger.logError(e)
        }
    }

    init {
        repo.getSettings(onSettingsRetrievedCallback)
    }

    override fun getFloat(key: String?, defValue: Float) =
        when(key){
            SETTINGS_MAX_HUM_KEY -> settings.maxHum ?: defValue
            SETTINGS_MAX_TEMP_KEY -> settings.maxTemp ?: defValue
            SETTINGS_MIN_HUM_KEY -> settings.minHum ?: defValue
            SETTINGS_MIN_TEMP_KEY -> settings.minTemp ?: defValue
            else -> defValue
        }

}