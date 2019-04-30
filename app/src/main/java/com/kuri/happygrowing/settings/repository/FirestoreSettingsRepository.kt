package com.kuri.happygrowing.settings.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Source
import com.kuri.happygrowing.settings.model.Settings
import com.kuri.happygrowing.shared.SETTINGS_COLLECTION
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.shared.logging.ILogger

internal class FirestoreSettingsRepository(private val settingsCollection: CollectionReference,
                                           private val logger: ILogger): ISettingsRepository {
    /**
     * Gets the settings for the current user.
     * This method tries to get the settings from the local storage first. If this operation fails, the settings
     * from the server will be queried.
     * If no settings were found neither on local storage nor in the server, a Settings object with all its values set
     * to 0 is returned.
     * @param callback Callback to be called when a result is returned or an exception is thrown.
     */
    override fun getSettings(callback: OnResultCallback<Settings>) {
        getSettings(callback, Source.CACHE)
    }

    /**
     * Gets the settings fro the specified source. If the operation fails and the source is local storage, a retry is performed
     * with the source set to the server.
     */
    private fun getSettings(callback: OnResultCallback<Settings>, source: Source){
        settingsCollection.document(SETTINGS_COLLECTION).get(source).addOnSuccessListener{
            val settings = if(it.exists()) it.toObject(Settings::class.java) else Settings(0f, 0f, 0f, 0f)
            if(settings == null){
                logger.logError("Could not cast result to settings object: $it")
                callback.onSuccessResult(Settings(0f,0f,0f,0f))
            } else {
                callback.onSuccessResult(settings)
            }
        }.addOnFailureListener {
            if(source == Source.CACHE){
                getSettings(callback, Source.SERVER)
            } else {
                callback.onError(it)
            }
        }
    }
}