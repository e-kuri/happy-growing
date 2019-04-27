package com.kuri.happygrowing.stats.repository.measurement

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.kuri.happygrowing.shared.logging.ILogger
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType
import kotlin.IllegalArgumentException

/**
 * CloudFirestore implementation for IMeasurement repository.
 */
internal class FirestoreMeasurementRepository(private val statsCollection: CollectionReference,
                                              private val logger: ILogger) : IMeasurementRepository {
    override fun listenMeasurementBySensor(
        type: SensorType,
        resultSize: Long?,
        resultCallback: OnRepositoryResult<List<Measurement>>,
        sortAsc: Boolean
    ) {
        if(type.equals(SensorType.UNKNOWN)){
            logger.logError("Requested measurements for unknown sensor type")
            throw IllegalArgumentException("Unknown is not a valid sensor type.")
        }
        var query = statsCollection.whereEqualTo(MEASUREMENT_TYPE_KEY, type.toString()).orderBy(MEASUREMENT_TIMESTAMP_KEY,
            if (sortAsc) Query.Direction.ASCENDING else Query.Direction.DESCENDING)
        if(resultSize != null) query.limit(resultSize)
        query.addSnapshotListener{ snapshot, e ->
            if(e != null){
                logger.logError("Failed to update stats: ${e.message}")
                resultCallback.onError(e)
            } else {
                if(snapshot != null) {
                    resultCallback.onSuccessResult(snapshot.toObjects(Measurement::class.java))
                } else {
                    resultCallback.onSuccessResult(listOf())
                }
            }
        }
    }

}