package com.kuri.happygrowing.stats.repository.measurement

import androidx.lifecycle.MutableLiveData
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType

interface IMeasurementRepository {
    fun getLastForAllSensors(resultCallback: OnRepositoryResult<Map<SensorType, Measurement>>)
}