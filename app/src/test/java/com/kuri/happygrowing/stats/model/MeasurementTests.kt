package com.kuri.happygrowing.stats.model

import org.junit.Assert
import org.junit.Test
import java.lang.IllegalArgumentException
import java.util.*

class MeasurementTests {

    @Test
    fun measurementTest_ExistingTypeLowercase(){
        val measurement = Measurement(0f, Date(), "temperature")
        Assert.assertTrue(measurement.type == SensorType.TEMPERATURE)
    }

    @Test
    fun measurementTest_ExistingTypeUppercase(){
        val measurement = Measurement(0f, Date(), "HUMIDITY")
        Assert.assertTrue(measurement.type == SensorType.HUMIDITY)
    }

    @Test(expected = IllegalArgumentException::class)
    fun measurementTest_UnexistingType(){
        val measurement = Measurement(0f, Date(), "papas")
    }

}