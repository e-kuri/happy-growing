package com.kuri.happygrowing.stats.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuri.happygrowing.shared.logging.getLogger
import com.kuri.happygrowing.stats.repository.measurement.StatsRepositoryFactory

object StatsViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = CurrentStatsViewModel(StatsRepositoryFactory.repo, getLogger()) as T
}