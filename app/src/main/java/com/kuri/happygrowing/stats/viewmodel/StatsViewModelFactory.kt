package com.kuri.happygrowing.stats.viewmodel

import androidx.lifecycle.LifecycleOwner
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.shared.logging.getLogger
import com.kuri.happygrowing.shared.repository.RepositoryFactory
import com.kuri.happygrowing.stats.model.Measurement

object StatsViewModelFactory {

    fun getViewModel(owner: LifecycleOwner, callback: OnResultCallback<List<Measurement>>) : CurrentStatsViewModel{
        return CurrentStatsViewModel(owner.lifecycle, RepositoryFactory.statsRepository, getLogger(), callback)
    }
}