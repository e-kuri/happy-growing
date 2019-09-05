package com.kuri.happygrowing.settings.repository

import com.kuri.happygrowing.settings.model.Settings
import com.kuri.happygrowing.shared.callback.OnResultCallback

interface ISettingsRepository {

    /**
     * Gets the settings for the current user.
     * @param callback Callback to be called with the result or the error.
     */
    fun getSettings(callback: OnResultCallback<Settings>)

    /**
     * Creates or updates the required setting in the repository.
     * @param key key of the setting that will be updated or created.
     * @param value vaue of the setting to be created or updated.
     * @param callback callback to be called when the set operation is complete.
     */
    fun setSetting(key: String, value: Any, callback: OnResultCallback<Void?>)

}