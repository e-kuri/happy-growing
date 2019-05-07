package com.kuri.happygrowing.settings.viewmodel

import android.preference.PreferenceDataStore
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModel
import com.kuri.happygrowing.settings.repository.ISettingsRepository
import com.kuri.happygrowing.shared.logging.ILogger

class SettingsViewModel(private val repo: ISettingsRepository, private val logger: ILogger,
                        private val prefDataStore: PreferenceDataStore) : ViewModel() {

    fun setDataStore(preferenceManager: PreferenceManager) {
        preferenceManager.preferenceDataStore = prefDataStore
    }

}