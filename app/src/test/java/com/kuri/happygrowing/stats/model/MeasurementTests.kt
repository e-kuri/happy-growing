package com.kuri.happygrowing.stats.model

import org.junit.Assert
import org.junit.Test
import java.util.*

class MeasurementTests {

    @Test
    fun measurementTest_ExistingTypeLowercase(){
        val measurement = Measurement(0f, Date(), "temperature")
        Assert.assertTrue(measurement.sensorType == SensorType.TEMPERATURE)
    }

    @Test
    fun measurementTest_ExistingTypeUppercase(){
        val measurement = Measurement(0f, Date(), "HUMIDITY")
        Assert.assertTrue(measurement.sensorType == SensorType.HUMIDITY)
    }

    @Test
    fun measurementTest_UnexistingType(){
         val measurement = Measurement(0f, Date(), "papas")
        Assert.assertTrue(measurement.sensorType == SensorType.UNKNOWN)
    }

}