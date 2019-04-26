package com.kuri.happygrowing.stats.repository.measurement

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.kuri.happygrowing.shared.logging.getLogger

class RepositoryFactory{

    private val userDoc = FirebaseFirestore.getInstance().collection("User")
        .document("XfBKFl8MkcYGqMDzE5u1")
    private val measurementColl = userDoc.collection(MEASUREMENT_COLLECTION)
    private val lastMeasurementColl = userDoc.collection(LAST_STATS_COLLECTION)

    companion object{
        const val TAG = "FirestoreMeasurementRep"
    }

    val measurementRepository: IMeasurementRepository = FirestoreMeasurementRepository(measurementColl, lastMeasurementColl, getLogger())


}