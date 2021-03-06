package com.kuri.happygrowing.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.kuri.happygrowing.settings.worker.SetSettingWorker
import com.kuri.happygrowing.settings.repository.ISettingsRepository
import com.kuri.happygrowing.shared.logging.ILogger

class SettingsViewModel(val repo: ISettingsRepository, val logger: ILogger) : ViewModel() {

    companion object {
        /**
         * Sets the preference in the repository asynchronously.
         */
        fun setPreference(key: String, value: String) {
            val settingData = workDataOf(SetSettingWorker.KEY to key,
                SetSettingWorker.VALUE to value)
            val setSettingWorkRequest = OneTimeWorkRequestBuilder<SetSettingWorker>()
                .setInputData(settingData)
                .build()
            WorkManager.getInstance().enqueue(setSettingWorkRequest)
        }
    }

}