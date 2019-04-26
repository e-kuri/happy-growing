package com.kuri.happygrowing.stats.repository.measurement

import com.google.firebase.firestore.CollectionReference
import com.kuri.happygrowing.shared.logging.ILogger
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType
import java.lang.IllegalArgumentException
import java.util.*

/**
 * CloudFirestore implementation for IMeasurement repository.
 */
internal class FirestoreMeasurementRepository(private val measurmentColl: CollectionReference, private val lastMeasurmentColl : CollectionReference,
                                              private val logger: ILogger) : IMeasurementRepository {

    /**
     * Gets the last measurement for each the sensor in the repository.
     * If the sensor type is not known, it will be ignored
     * @param resultCallback callback to be called once the data has been returned or to notify if the operation failed
     */
    override fun getLastForAllSensors(resultCallback: OnRepositoryResult<Map<SensorType, Measurement>>) {
        lastMeasurmentColl.get().addOnSuccessListener {
            val resultMap = it.documents.asSequence().map { doc ->
                try{
                    doc.toObject(Measurement::class.java)
                }catch (e: IllegalArgumentException){
                    logger.logError("Failed to cast document to measurement $doc")
                    null
                }
            }.filter { msr -> msr != null }.associate { msr -> Pair(msr!!.type, msr) }
            resultCallback.onSuccessResult(resultMap)
        }.addOnFailureListener { resultCallback.onError(it) }
    }
}