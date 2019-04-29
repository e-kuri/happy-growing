package com.kuri.happygrowing.stats.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuri.happygrowing.shared.logging.ILogger
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType
import com.kuri.happygrowing.stats.repository.measurement.IMeasurementRepository
import com.kuri.happygrowing.stats.repository.measurement.OnRepositoryResult

class CurrentStatsViewModel(private val repo: IMeasurementRepository, private val logger: ILogger) : ViewModel() {

    /**
     * Inner class that extends mutableLiveData that holds a map of sensorType to Measurement.
     * This class overrides the onActive and onInactive functions of its parent class in order to remove the listeners
     * in the repository when the user is no longer requiring them.
     */
    inner class MeasurementsLiveData : MutableLiveData<Map<SensorType, Measurement>>(){
        /**
         * Starts listening to the changes in the repository to update the live data when they occur.
         */
        override fun onActive() {
            super.onActive()
            loadMeasurements()
        }

        /**
         * Stops listening to changes in the repository when they are no longer visible for the user.
         */
        override fun onInactive() {
            super.onInactive()
            repo.stopListening()
        }
    }

    /**
     * LiveData holding a map of sensorType to Measurement.
     * This LiveData maps the sensorType to the last measurement in the repository, ready to be displayed to the user.
     */
    val measurements = MeasurementsLiveData()

    /**
     * OnRepositoryResult callback to update the measurements liveData as needed.
     * This callback is shared between the listeners to each of the sensors.
     */
    private val onStatsReceivedCallback = object: OnRepositoryResult<List<Measurement>>{

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

    /**
     * Set the string values of the measurements to be ready to be displayed to the user.
     * @param measurement Measurement whose stringValue will be updates according to its sensorType.
     */
    private fun setStringValue(measurement: Measurement){
        measurement.stringValue = when(measurement.sensorType){
            SensorType.HUMIDITY -> "${measurement.value} %"
            SensorType.TEMPERATURE -> "${measurement.value} °C"
            else -> measurement.value.toString()
        }
    }

    /**
     * Starts listening to the changes in the repository for each of the available sensors.
     */
    private fun loadMeasurements(){
        SensorType.values().forEach {
            if(it != SensorType.UNKNOWN) repo.listenMeasurementBySensor(it, 2, onStatsReceivedCallback) }
    }

}