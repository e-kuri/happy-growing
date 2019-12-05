package com.kuri.happygrowing.settings.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.shared.repository.RepositoryFactory
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class SetSettingWorker(appContext: Context, workerParameters: WorkerParameters)
    : Worker(appContext, workerParameters){

    companion object {
        const val KEY = "key"
        const val VALUE = "value"
        private const val TIMEOUT_SECONDS = 60L
    }

    override fun doWork(): Result {
        val countDownLatch = CountDownLatch(1)
        val key = inputData.getString(KEY)
        val value = inputData.getString(VALUE)
        var success = false

        if(key == null || value == null) {
            return Result.failure()
        }

        val callback = object: OnResultCallback<Void?> {
            override fun onSuccessResult(result: Void?) {
                success = true
                countDownLatch.countDown()
            }

            override fun onError(e: Exception) {
                countDownLatch.countDown()
            }
        }

        val settingsRepository = RepositoryFactory.settingsRepository
        settingsRepository.setSetting(key, value.toFloat(), callback)
        countDownLatch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        if(success) {
            return Result.success()
        }
        return Result.failure()
    }
}