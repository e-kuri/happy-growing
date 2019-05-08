package com.kuri.happygrowing.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuri.happygrowing.settings.viewmodel.SettingsViewModel
import com.kuri.happygrowing.shared.exceptions.InvalidViewModel
import com.kuri.happygrowing.shared.logging.getLogger
import com.kuri.happygrowing.shared.repository.RepositoryFactory

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            SettingsViewModel::class.java -> SettingsViewModel(RepositoryFactory.settingsRepository, getLogger()) as T
            else -> throw InvalidViewModel("View model not found")
        }
    }

}