package com.kuri.happygrowing.stats.repository.measurement

import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType

interface IMeasurementRepository {
    fun getLastBySensorType(types: Collection<SensorType>): Map<SensorType, Measurement>
}