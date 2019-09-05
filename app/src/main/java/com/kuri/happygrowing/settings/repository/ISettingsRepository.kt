package com.kuri.happygrowing.settings.repository

import com.kuri.happygrowing.settings.model.Settings
import com.kuri.happygrowing.shared.callback.OnResultCallback

interface ISettingsRepository {

    /**
     * Gets the settings for the current user.
     * @param callback Callback to be called with the result or the error.
     */
    fun getSettings(callback: OnResultCallback<Settings>)

    fun setSetting(key: String, value: Float, callback: OnResultCallback<Void?>)

}