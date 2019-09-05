package com.kuri.happygrowing.settings.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.kuri.happygrowing.settings.model.Settings
import com.kuri.happygrowing.shared.DbConstants
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.shared.logging.ILogger

internal class FirestoreSettingsRepository(private val settingsCollection: CollectionReference,
                                           private val logger: ILogger): ISettingsRepository {

    /**
     * Creates or updates the setting with the specfied key and value.
     * @param key of the setting to be updated.
     * @param value value of the setting to be created or updated.
     * @param callback Callback to be called after the creation or update is complete.
     */
    override fun setSetting(key: String, value: Float, callback: OnResultCallback<Void?>) {
        val data = hashMapOf(key to value)
        settingsCollection.document(DbConstants.SETTINGS_COLLECTION).set(data, SetOptions.merge()).addOnSuccessListener {
            callback.onSuccessResult(it)
        }.addOnFailureListener {
            callback.onError(it)
        }
    }

    /**
     * Gets the settings for the current user.
     * If no settings were found, a Settings object with all its values set to 0 is returned.
     * @param callback Callback to be called when a result is returned or an exception is thrown.
     */
    override fun getSettings(callback: OnResultCallback<Settings>) {
        settingsCollection.document(DbConstants.SETTINGS_COLLECTION).get().addOnSuccessListener{
            if(it.exists()) {
                val settings = it.toObject(Settings::class.java)
                if(settings == null){
                    logger.logError("Could not cast result to settings object: $it")
                    callback.onError(ClassCastException("Could not cast result to settings object: $it"))
                } else {
                    callback.onSuccessResult(settings)
                }
            } else {
                callback.onSuccessResult(Settings())
            }
        }.addOnFailureListener {
            callback.onError(it)
        }
    }

}