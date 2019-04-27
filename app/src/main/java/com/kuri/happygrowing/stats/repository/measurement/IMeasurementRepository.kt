package com.kuri.happygrowing.stats.repository.measurement

import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType

interface IMeasurementRepository {
    //fun getLastForAllSensors(resultCallback: OnRepositoryResult<Map<SensorType, Measurement>>)

    fun listenMeasurementBySensor(type: SensorType, resultSize: Long?, resultCallback: OnRepositoryResult<List<Measurement>>,
                         sortAsc: Boolean = true)
}