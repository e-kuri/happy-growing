package com.kuri.happygrowing.stats.viewmodel

import android.hardware.Sensor
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

    private val onStatsReceived = object: OnRepositoryResult<Map<SensorType, Measurement>>{
        override fun onSuccessResult(result: Map<SensorType, Measurement>) {
            measurements.postValue(result)
        }

        override fun onError(e: Exception) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    fun getMeasurments(): LiveData<Map<SensorType, Measurement>> = measurements

    fun loadMeasurements(){
        repo.getLastForAllSensors(onStatsReceived)
    }

}