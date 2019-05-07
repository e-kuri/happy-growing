package com.kuri.happygrowing.settings.repository

import com.kuri.happygrowing.settings.model.Settings
import com.kuri.happygrowing.shared.TestLogger
import com.kuri.happygrowing.shared.callback.OnResultCallback
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.*

@RunWith(MockitoJUnitRunner::class)
class PreferenceDataStoreTests {

    @Mock
    private lateinit var repository: ISettingsRepository

    private val logger = TestLogger()

    @Test
    fun getFloat_ValidTest(){
        val settings = Settings(10f, 20f, 30f, 40f)
        Mockito.`when`(repository.getSettings(ArgumentMatchers.any())).then{
            var callback = it.getArgument(0) as OnResultCallback<Settings>
            callback.onSuccessResult(settings)

        }
    }

}