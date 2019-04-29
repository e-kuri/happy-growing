package com.kuri.happygrowing.stats.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.kuri.happygrowing.shared.logging.ILogger
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType
import com.kuri.happygrowing.stats.repository.measurement.IMeasurementRepository
import com.kuri.happygrowing.shared.callback.OnResultCallback

class CurrentStatsViewModel(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PRIVATE) val lifecycle: Lifecycle,
    private val repo: IMeasurementRepository,
    private val logger: ILogger,
    private val callback: OnResultCallback<Map<SensorType, Measurement>>) : LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    /**
     * map where the results are stored every time a change occurs in the repository
     */
    @get:VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val measurements = mutableMapOf<SensorType, Measurement>()

    /**
     * OnResultCallback callback to update the measurements liveData as needed.
     * This callback is shared between the listeners to each of the sensors.
     */
    private val onStatsReceivedCallback = object:
        OnResultCallback<List<Measurement>> {

        override fun onSuccessResult(result: List<Measurement>) {
            if(result.isNotEmpty()){
                val updated = result[result.size - 1]
                if(result.size == 2){
                    updated.diff = updated.value - result[0].value
                }
                setStringValue(updated)
                measurements[updated.sensorType] = updated
                callback.onSuccessResult(measurements)
            }
        }

        override fun onError(e: Exception) {
            logger.logError(e.message ?: e.toString())
            callback.onError(e)
        }

    }

    /**
     * Starts listening to the changes in the repository for each of the available sensors.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun startListening(){
        SensorType.values().forEach {
            if(it != SensorType.UNKNOWN) repo.listenMeasurementBySensor(it, 2, onStatsReceivedCallback) }
    }

    /**
     * Stops listening to changes in the repository when they are no longer visible for the user.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun stopListening(){
        repo.stopListening()
    }

    /**
     * Detach the observer from the lifecycle owner to avoid memory leaks
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun detach(){
        lifecycle.removeObserver(this)
        measurements.clear()
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

}
