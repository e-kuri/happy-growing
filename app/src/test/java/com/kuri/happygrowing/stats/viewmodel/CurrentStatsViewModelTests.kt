package com.kuri.happygrowing.stats.viewmodel

import com.google.firebase.firestore.FirebaseFirestoreException
import com.kuri.happygrowing.shared.LifecycleOwnerStub
import com.kuri.happygrowing.shared.any
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.shared.logging.ILogger
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType
import com.kuri.happygrowing.stats.repository.measurement.IMeasurementRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class CurrentStatsViewModelTests {

    @Mock
    private lateinit var logger : ILogger

    private var lifecycleOwner : LifecycleOwnerStub? = null

    @Mock
    private lateinit var repo: IMeasurementRepository

    @Mock
    private lateinit var resultCallback: OnResultCallback<List<Measurement>>


    @Before
    fun setUp(){
        lifecycleOwner = LifecycleOwnerStub()
        Mockito.`when`(repo!!.listenMeasurementBySensor(any(), Mockito.anyLong(),
            any(), Mockito.anyBoolean())).then {
            val sensorType = it.arguments[0] as SensorType
            val callback = it.arguments[2] as OnResultCallback<List<Measurement>>
            val measurement = when(sensorType) {
                SensorType.TEMPERATURE -> Measurement(type = "temperature", value = 10f, date = Date())
                SensorType.HUMIDITY -> Measurement(type = "humidity", value = 20f, date = Date())
                else -> Measurement()
            }
            callback.onSuccessResult(listOf(measurement))
        }
    }

    @Test
    fun startListening(){
        CurrentStatsViewModel(lifecycleOwner!!.lifecycle, repo!!, logger, resultCallback)
        lifecycleOwner!!.onCreate()
        lifecycleOwner!!.onStart()
        Mockito.verify(repo!!, Mockito.never())
            .listenMeasurementBySensor(any(), Mockito.anyLong(), any(), Mockito.anyBoolean())
        lifecycleOwner!!.onResume()
        Mockito.verify(repo!!, Mockito.times(2))
            .listenMeasurementBySensor(any(), Mockito.anyLong(), any(), Mockito.anyBoolean())
    }


    @Test
    fun stopListening(){
        CurrentStatsViewModel(lifecycleOwner!!.lifecycle, repo!!, logger, resultCallback)
        lifecycleOwner!!.onCreate()
        lifecycleOwner!!.onStart()
        lifecycleOwner!!.onResume()
        Mockito.verify(repo!!, Mockito.never()).stopListening()
        lifecycleOwner!!.onPause()
        Mockito.verify(repo!!, Mockito.times(1)).stopListening()
    }

    @Test
    fun restartListening(){
        CurrentStatsViewModel(lifecycleOwner!!.lifecycle, repo!!, logger, resultCallback)
        lifecycleOwner!!.onCreate()
        lifecycleOwner!!.onStart()
        lifecycleOwner!!.onResume()
        lifecycleOwner!!.onPause()
        lifecycleOwner!!.onStop()
        lifecycleOwner!!.onStart()
        lifecycleOwner!!.onResume()
        Mockito.verify(repo!!, Mockito.times(4))
            .listenMeasurementBySensor(any(), Mockito.anyLong(), any(), Mockito.anyBoolean())
    }

    @Test
    fun cleanInformation(){
        val viewModel = CurrentStatsViewModel(lifecycleOwner!!.lifecycle, repo!!, logger, resultCallback)
        lifecycleOwner!!.onCreate()
        lifecycleOwner!!.onStart()
        lifecycleOwner!!.onResume()
        lifecycleOwner!!.onPause()
        lifecycleOwner!!.onStop()
        lifecycleOwner!!.onDestroy()
        Assert.assertEquals(0, viewModel.measurements.size)
    }

    @Test
    fun validateResultCallbackCalls(){
        CurrentStatsViewModel(lifecycleOwner!!.lifecycle, repo!!, logger, resultCallback)
        lifecycleOwner!!.onCreate()
        lifecycleOwner!!.onStart()
        lifecycleOwner!!.onResume()
        Mockito.verify(resultCallback, Mockito.times(2)).onSuccessResult(any())
    }

    @Test
    fun validateResultCallbackContent(){
        var result = listOf<Measurement>()
        val resultCallback = object: OnResultCallback<List<Measurement>>{
            override fun onSuccessResult(res: List<Measurement>) {
                result = res
            }

            override fun onError(e: Exception) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
        CurrentStatsViewModel(lifecycleOwner!!.lifecycle, repo!!, logger, resultCallback)
        lifecycleOwner!!.onCreate()
        lifecycleOwner!!.onStart()
        lifecycleOwner!!.onResume()
        Assert.assertEquals(2, result.size)
        result.forEach{
            when {
                it.sensorType == SensorType.HUMIDITY -> Assert.assertEquals("20.0 %", it.stringValue)
                it.sensorType == SensorType.TEMPERATURE -> Assert.assertEquals("10.0 °C", it.stringValue)
                else -> throw IllegalArgumentException()
            }
        }
    }

    @Test
    fun validateIncrements(){
        val repo = Mockito.mock(IMeasurementRepository::class.java)
        Mockito.`when`(repo!!.listenMeasurementBySensor(any(), Mockito.anyLong(),
            any(), Mockito.anyBoolean())).then {
            val sensorType = it.arguments[0] as SensorType
            val callback = it.arguments[2] as OnResultCallback<List<Measurement>>
            val measurements = when(sensorType) {
                SensorType.TEMPERATURE -> listOf(
                    Measurement(type = "temperature", value = 12f, date = Date(Date().time - 3600)),
                    Measurement(type = "temperature", value = 10f, date = Date())
                )
                SensorType.HUMIDITY -> listOf(
                    Measurement(type = "humidity", value = 12f, date = Date(Date().time - 3600)),
                    Measurement(type = "humidity", value = 14f, date = Date())
                )
                else -> listOf(Measurement())
            }
            callback.onSuccessResult(measurements)
        }
        var result = listOf<Measurement>()
        val resultCallback = object: OnResultCallback<List<Measurement>>{
            override fun onSuccessResult(res: List<Measurement>) {
                result = res
            }

            override fun onError(e: Exception) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
        CurrentStatsViewModel(lifecycleOwner!!.lifecycle, repo!!, logger, resultCallback)
        lifecycleOwner!!.onCreate()
        lifecycleOwner!!.onStart()
        lifecycleOwner!!.onResume()
        Assert.assertEquals(2, result.size)
        result.forEach{
            when {
                it.sensorType == SensorType.HUMIDITY -> Assert.assertEquals(2f, it.diff)
                it.sensorType == SensorType.TEMPERATURE -> Assert.assertEquals(-2f, it.diff)
                else -> throw IllegalArgumentException()
            }
        }
    }

    @Test
    fun validateErrorLog(){
        val errorMsg = "tacos dorados"
        val repo = Mockito.mock(IMeasurementRepository::class.java)
        Mockito.`when`(repo!!.listenMeasurementBySensor(any(), Mockito.anyLong(),
            any(), Mockito.anyBoolean())).then {
            val callback = it.arguments[2] as OnResultCallback<List<Measurement>>
            val error = Mockito.mock(FirebaseFirestoreException::class.java)
            Mockito.`when`(error.message).thenReturn(errorMsg)
            callback.onError(error)
        }
        var result = mapOf<SensorType, Measurement>()
        val resultCallback = object: OnResultCallback<List<Measurement>>{
            override fun onSuccessResult(res: List<Measurement>) {

            }

            override fun onError(e: Exception) {
            }

        }
        CurrentStatsViewModel(lifecycleOwner!!.lifecycle, repo!!, logger, resultCallback)
        lifecycleOwner!!.onCreate()
        lifecycleOwner!!.onStart()
        lifecycleOwner!!.onResume()
        Mockito.verify(logger, Mockito.times(2)).logError(errorMsg)
    }

    @Test
    fun validateError(){
        val error = Mockito.mock(FirebaseFirestoreException::class.java)
        val repo = Mockito.mock(IMeasurementRepository::class.java)
        Mockito.`when`(repo!!.listenMeasurementBySensor(any(), Mockito.anyLong(),
            any(), Mockito.anyBoolean())).then {
            val callback = it.arguments[2] as OnResultCallback<List<Measurement>>
            Mockito.`when`(error.message).thenReturn(null)
            callback.onError(error)
        }
        var result = mapOf<SensorType, Measurement>()
        CurrentStatsViewModel(lifecycleOwner!!.lifecycle, repo!!, logger, resultCallback)
        lifecycleOwner!!.onCreate()
        lifecycleOwner!!.onStart()
        lifecycleOwner!!.onResume()
        Mockito.verify(resultCallback, Mockito.times(2)).onError(error)
    }
}