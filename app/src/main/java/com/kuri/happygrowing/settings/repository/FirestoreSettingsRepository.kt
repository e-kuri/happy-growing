package com.kuri.happygrowing.settings.repository

import com.google.firebase.firestore.CollectionReference
import com.kuri.happygrowing.settings.model.Settings
import com.kuri.happygrowing.shared.SETTINGS_COLLECTION
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.shared.logging.ILogger

internal class FirestoreSettingsRepository(private val settingsCollection: CollectionReference,
                                           private val logger: ILogger): ISettingsRepository {
    override fun setValue(key: String, value: Any, callback: OnResultCallback<Boolean>) {
        settingsCollection.document(SETTINGS_COLLECTION).update(key, value).addOnSuccessListener {
            callback.onSuccessResult(true)
        }.addOnFailureListener{
            callback.onError(it)
        }
    }

    /**
     * Gets the settings for the current user.
     * @param callback Callback to be called when a result is returned or an exception is thrown.
     */
    override fun getSettings(callback: OnResultCallback<Settings>) {
        settingsCollection.document(SETTINGS_COLLECTION).get().addOnSuccessListener {
            if(it.exists()) {
                val settings = it.toObject(Settings::class.java)
                if(settings == null){
                    logger.logError("Could not cast result to settings object: $it")
                    callback.onError(ClassCastException("Could not cast result to settings object: $it"))
                } else {
                    callback.onSuccessResult(settings)
                }
            }
        }.addOnFailureListener {
            callback.onError(it)
        }
    }
}