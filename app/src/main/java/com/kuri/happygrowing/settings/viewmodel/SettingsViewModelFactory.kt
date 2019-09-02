package com.kuri.happygrowing.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuri.happygrowing.shared.InvalidTypeException
import com.kuri.happygrowing.shared.logging.getLogger
import com.kuri.happygrowing.shared.repository.RepositoryFactory

object SettingsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(RepositoryFactory.settingsRepository, getLogger()) as T
        }
        throw InvalidTypeException("Class should be of type SettingsViewModel")
    }


}