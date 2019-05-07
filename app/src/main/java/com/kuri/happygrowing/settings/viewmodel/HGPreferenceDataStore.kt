package com.kuri.happygrowing.settings.viewmodel

import androidx.preference.PreferenceDataStore
import com.kuri.happygrowing.settings.model.Settings
import com.kuri.happygrowing.settings.repository.ISettingsRepository
import com.kuri.happygrowing.shared.SETTINGS_MAX_HUM_KEY
import com.kuri.happygrowing.shared.SETTINGS_MAX_TEMP_KEY
import com.kuri.happygrowing.shared.SETTINGS_MIN_HUM_KEY
import com.kuri.happygrowing.shared.SETTINGS_MIN_TEMP_KEY
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.shared.logging.ILogger

    class HGPreferenceDataStore(private val repo: ISettingsRepository, private val logger: ILogger): PreferenceDataStore() {

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

    override fun putFloat(key: String?, value: Float) {
        val updated = Settings(settings.minTemp, settings.maxTemp, settings.minHum, settings.maxHum)
        when(key) {
            SETTINGS_MAX_HUM_KEY -> { updated.maxHum = value; sendUpdateRequest(updated) }
            SETTINGS_MAX_TEMP_KEY -> { updated.maxTemp = value; sendUpdateRequest(updated) }
            SETTINGS_MIN_HUM_KEY -> { updated.minHum = value; sendUpdateRequest(updated) }
            SETTINGS_MIN_TEMP_KEY -> { updated.minTemp = value; sendUpdateRequest(updated) }
        }
    }

    private fun sendUpdateRequest(settings: Settings){
        repo.setSettings(settings, object: OnResultCallback<Boolean> {
            override fun onSuccessResult(result: Boolean) {
                this@HGPreferenceDataStore.settings = settings
            }

            override fun onError(e: Exception) {
                logger.logError(e)
            }

        })
    }

}