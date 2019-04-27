package com.kuri.happygrowing.stats.repository.measurement

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kuri.happygrowing.shared.logging.getLogger

object StatsRepositoryFactory{

    const val TAG = "FirestoreMeasurementRep"
    private val userDoc = FirebaseFirestore.getInstance().collection("User")
        .document("XfBKFl8MkcYGqMDzE5u1")
    private val measurementColl: CollectionReference by lazy {  userDoc.collection(MEASUREMENT_COLLECTION) }
    private val lastMeasurementColl: CollectionReference by lazy { userDoc.collection(LAST_STATS_COLLECTION) }
    val repo: IMeasurementRepository by lazy {
        FirestoreMeasurementRepository(measurementColl, lastMeasurementColl, getLogger())
    }
}