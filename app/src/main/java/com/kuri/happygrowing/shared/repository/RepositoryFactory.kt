package com.kuri.happygrowing.shared.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kuri.happygrowing.settings.repository.FirestoreSettingsRepository
import com.kuri.happygrowing.settings.repository.ISettingsRepository
import com.kuri.happygrowing.shared.DbConstants
import com.kuri.happygrowing.shared.logging.getLogger
import com.kuri.happygrowing.stats.repository.measurement.FirestoreMeasurementRepository
import com.kuri.happygrowing.stats.repository.measurement.IMeasurementRepository

internal object RepositoryFactory{

    private const val USER_COLLECTION = "User"
    private const val USER_ID = "XfBKFl8MkcYGqMDzE5u1"
    private val userDoc: DocumentReference by lazy {
        FirebaseFirestore.getInstance().collection(USER_COLLECTION).document(USER_ID)
    }

    private val statsCollection: CollectionReference by lazy { userDoc.collection(DbConstants.STATS_COLLECTION) }
    private val settingsCollection : CollectionReference by lazy { userDoc.collection(DbConstants.SETTINGS_COLLECTION) }

    val statsRepository: IMeasurementRepository by lazy { FirestoreMeasurementRepository(statsCollection, getLogger()) }
    val settingsRepository: ISettingsRepository by lazy { FirestoreSettingsRepository(
        settingsCollection, getLogger())
    }

}
