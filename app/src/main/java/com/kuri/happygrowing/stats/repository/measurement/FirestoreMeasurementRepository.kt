package com.kuri.happygrowing.stats.repository.measurement

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.kuri.happygrowing.shared.DbConstants
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.shared.logging.ILogger
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType
import kotlin.IllegalArgumentException

/**
 * CloudFirestore implementation for IMeasurement repository.
 */
internal class FirestoreMeasurementRepository(private val statsCollection: CollectionReference,
                                              private val logger: ILogger) : IMeasurementRepository {

     val listeners = mutableSetOf<ListenerRegistration>()

    /**
     * Starts listening to the stats collection for the query that fulfills the parameters specs.
     * @param type Type of sensor required.
     * @param resultSize Number of elements in the resulting document set. if null, all the documents that fulfill the
     * specified conditions will be returned.
     * @param resultCallbackCallback Callback to be called after the query, to send either the result set or the error.
     * @param sortAsc If true, the search query will be done in ascending order, otherwise the sorting will be in
     * descending order.
     */
    override fun listenMeasurementBySensor(
        type: SensorType,
        resultSize: Long?,
        resultCallbackCallback: OnResultCallback<List<Measurement>>,
        sortAsc: Boolean
    ) {
        if(type == SensorType.UNKNOWN){
            logger.logError("Requested measurements for unknown sensor type")
            throw IllegalArgumentException("Unknown is not a valid sensor type.")
        }
        var query = statsCollection.whereEqualTo(DbConstants.MEASUREMENT_TYPE_KEY, type.toString()).orderBy(
            DbConstants.MEASUREMENT_TIMESTAMP_KEY,
            if (sortAsc) Query.Direction.ASCENDING else Query.Direction.DESCENDING)
        if(resultSize != null) query.limit(resultSize)
        listeners.add(query.addSnapshotListener{ snapshot, e ->
            if(e != null){
                logger.logError("Failed to update stats: ${e.message}")
                resultCallbackCallback.onError(e)
            } else {
                if(snapshot != null) {
                    resultCallbackCallback.onSuccessResult(snapshot.toObjects(Measurement::class.java))
                } else {
                    resultCallbackCallback.onSuccessResult(listOf())
                }
            }
        })
    }

    /**
     * Stop listening for changes in the repository
     */
    override fun stopListening() {
        listeners.forEach{
            it.remove()
        }
        listeners.clear()
    }


}