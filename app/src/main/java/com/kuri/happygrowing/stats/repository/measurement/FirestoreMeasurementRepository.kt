package com.kuri.happygrowing.stats.repository.measurement

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType

internal class FirestoreMeasurementRepository : IMeasurementRepository {

    private val db = FirebaseFirestore.getInstance().collection("User").document("XfBKFl8MkcYGqMDzE5u1")

    override fun getLastBySensorType(types: Collection<SensorType>): Map<SensorType, Measurement> {
        val result = mutableMapOf<SensorType, Measurement>()
        val successListener = OnSuccessListener<QuerySnapshot> {
            if(it != null && !it.isEmpty){
                val resultDoc = it.documents[0]?.toObject(Measurement::class.java)
                if(resultDoc != null){
                    result[resultDoc.type] = resultDoc
                }
            }
        }
        types.forEach {
            db.collection(MEASUREMENT_COLLECTION).orderBy(MEASUREMENT_TIMESTAMP_KEY, Query.Direction.DESCENDING)
                .whereEqualTo(MEASUREMENT_TYPE_KEY, it.toString()).limit(1).get().addOnSuccessListener (successListener)
        }
        return result
    }
}