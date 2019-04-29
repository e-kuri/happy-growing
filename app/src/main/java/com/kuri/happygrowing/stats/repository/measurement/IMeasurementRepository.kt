package com.kuri.happygrowing.stats.repository.measurement

import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType

interface IMeasurementRepository {

    /**
     * Starts listening to the stats collection for the query that fulfills the parameters specs.
     * @param type Type of sensor required.
     * @param resultSize Number of elements in the resulting document set. if null, all the documents that fulfill the
     * specified conditions will be returned.
     * @param resultCallbackCallback Callback to be called after the query, to send either the result set or the error.
     * @param sortAsc If true, the search query will be done in ascending order, otherwise the sorting will be in
     * descending order.
     */
    fun listenMeasurementBySensor(type: SensorType, resultSize: Long?, resultCallbackCallback: OnResultCallback<List<Measurement>>,
                                  sortAsc: Boolean = true)
    /**
     * Stop listening for changes in the repository.
     */
    fun stopListening()
}