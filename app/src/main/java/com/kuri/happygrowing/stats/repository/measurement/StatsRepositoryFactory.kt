package com.kuri.happygrowing.stats.repository.measurement

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kuri.happygrowing.shared.logging.getLogger

object StatsRepositoryFactory{

    const val TAG = "FirestoreMeasurementRep"
    private val userDoc = FirebaseFirestore.getInstance().collection("User")
        .document("XfBKFl8MkcYGqMDzE5u1")
    private val statsCollection: CollectionReference by lazy {  userDoc.collection(STATS_COLLECTION) }
    val repo: IMeasurementRepository by lazy {
        FirestoreMeasurementRepository(statsCollection, getLogger())
    }
}