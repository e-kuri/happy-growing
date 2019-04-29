package com.kuri.happygrowing.stats.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.kuri.happygrowing.shared.logging.ILogger
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.repository.measurement.IMeasurementRepository
import com.kuri.happygrowing.stats.repository.measurement.OnRepositoryResult
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class CurrentStatsViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val logger = object: ILogger{
        override fun logError(msg: String) {
            println(msg)
        }

        override fun logDebug(msg: String) {
            println(msg)
        }

        override fun logInfo(msg: String) {
            println(msg)
        }

    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

    @Test
    fun viewModelTests(){
        val repo = Mockito.mock(IMeasurementRepository::class.java)
        val viewModel = CurrentStatsViewModel(repo, logger)
        val result = listOf(Measurement(type = "temperature", value = 10f), Measurement( type = "humidity", value = 20f))
        Mockito.`when`(repo.listenMeasurementBySensor(any(), Mockito.anyLong(), any(), Mockito.anyBoolean()))
            .then {
                val callback = it.arguments[3]
                if(callback is OnRepositoryResult<*>){
                    (callback as OnRepositoryResult<List<Measurement>>).onSuccessResult(result)
                }
            }
        val lifecycleOwner = Mockito.mock(LifecycleOwner::class.java)
        val lifecycle = Mockito.mock(Lifecycle::class.java)
        Mockito.`when`(lifecycleOwner.lifecycle).thenReturn(lifecycle)
        Mockito.`when`(lifecycle.currentState).thenReturn(Lifecycle.State.STARTED)
        /*
        Mockito.`when`(lifecycle.addObserver(Mockito.any())).then {
            val observer = it.getArgument<LifecycleObserver>(0)

        }
        */
        viewModel.measurements.observe(lifecycleOwner, Observer {
            Assert.assertEquals(2, it.size)
        })
    }
}