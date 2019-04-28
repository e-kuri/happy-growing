package com.kuri.happygrowing.stats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuri.happygrowing.shared.logging.ILogger
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType
import com.kuri.happygrowing.stats.repository.measurement.IMeasurementRepository
import com.kuri.happygrowing.stats.repository.measurement.OnRepositoryResult

class CurrentStatsViewModel(private val repo: IMeasurementRepository, private val logger: ILogger) : ViewModel() {

    private val measurements: MutableLiveData<Map<SensorType, Measurement>> by lazy {
        MutableLiveData<Map<SensorType, Measurement>>().also {
            loadMeasurements()
        }
    }

    private val onStatsReceived = object: OnRepositoryResult<List<Measurement>>{
        override fun onSuccessResult(result: List<Measurement>) {
            if(result.isNotEmpty()){
                val updated = result[result.size - 1]
                if(result.size == 2){
                    updated.diff = updated.value - result[0].value
                }
                val msr = measurements.value?.toMutableMap() ?: mutableMapOf()
                setStringValue(updated)
                msr[updated.sensorType] = updated
                measurements.postValue(msr)
            }
        }

        override fun onError(e: Exception) {
            logger.logError(e.message ?: e.toString())
        }

    }

    private fun setStringValue(measurement: Measurement){
        measurement.stringValue = when(measurement.sensorType){
            SensorType.HUMIDITY -> "${measurement.value} %"
            SensorType.TEMPERATURE -> "${measurement.value} °C"
            else -> measurement.value.toString()
        }
    }

    fun getMeasurments(): LiveData<Map<SensorType, Measurement>> = measurements

    private fun loadMeasurements(){
        SensorType.values().forEach {
            if(it != SensorType.UNKNOWN) repo.listenMeasurementBySensor(it, 2, onStatsReceived) }
    }

}