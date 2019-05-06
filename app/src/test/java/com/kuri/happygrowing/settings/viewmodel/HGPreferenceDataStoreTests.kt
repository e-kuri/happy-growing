package com.kuri.happygrowing.settings.viewmodel

import com.kuri.happygrowing.settings.model.Settings
import com.kuri.happygrowing.settings.repository.ISettingsRepository
import com.kuri.happygrowing.shared.*
import com.kuri.happygrowing.shared.callback.OnResultCallback
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HGPreferenceDataStoreTests {

    private val logger = TestLogger()

    private val storedSettings = Settings(10f, 20f, 30f, 40f)

    @Test
    fun getMaxHumidity_SuccessTest() {
        val dataStore = getDataStoreWithSettings()
        Assert.assertEquals(40f, dataStore.getFloat(SETTINGS_MAX_HUM_KEY, 100f))
    }

    @Test
    fun getMinHumidity_SuccessTest() {
        val dataStore = getDataStoreWithSettings()
        Assert.assertEquals(30f, dataStore.getFloat(SETTINGS_MIN_HUM_KEY, 100f))
    }

    @Test
    fun getMaxTemperature_SuccessTest() {
        val dataStore = getDataStoreWithSettings()
        Assert.assertEquals(20f, dataStore.getFloat(SETTINGS_MAX_TEMP_KEY, 100f))
    }

    @Test
    fun getMinTemperature_SuccessTest() {
        val dataStore = getDataStoreWithSettings()
        Assert.assertEquals(10f, dataStore.getFloat(SETTINGS_MIN_TEMP_KEY, 100f))
    }

    @Test
    fun getFloat_DefaultTest() {
        val dataStore = getDataStoreWithSettings()
        Assert.assertEquals(100f, dataStore.getFloat("none", 100f))
    }

    @Test
    fun getFloat_NullTest() {
        val dataStore = getDataStoreWithSettings()
        Assert.assertEquals(100f, dataStore.getFloat(null, 100f))
    }

    private fun getDataStoreWithSettings() : HGPreferenceDataStore {
        val repo = mock(ISettingsRepository::class.java)
        `when`(repo.getSettings(com.kuri.happygrowing.shared.any())).then {
            val callback = it.arguments[0] as OnResultCallback<Settings>
            callback.onSuccessResult(storedSettings)
        }
        return HGPreferenceDataStore(repo, logger)
    }

}