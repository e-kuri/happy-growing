package com.kuri.happygrowing.stats.model

import java.lang.IllegalArgumentException
import java.util.*

enum class SensorType {
    TEMPERATURE,
    HUMIDITY,
    UNKNOWN
}

class Measurement(var value: Float = 0f, var date: Date = Date(0), type: String = "unknown", var diff: Float = 0.0f,
                  var stringValue: String = "") {

    var type: String = type
    set(value) {
        sensorType = try {
            SensorType.valueOf(value.toUpperCase())
        }catch (e: IllegalArgumentException){
            SensorType.UNKNOWN
        }
        field = sensorType.toString()
    }

    var sensorType = try {
        SensorType.valueOf(type.toUpperCase())
    }catch (e: IllegalArgumentException){
        SensorType.UNKNOWN
    }
    private set

}