package com.kuri.happygrowing.stats.model

import java.lang.IllegalArgumentException
import java.util.*

enum class SensorType {
    TEMPERATURE,
    HUMIDITY,
    UNKNOWN
}

class Measurement(var value: Float = 0f, var date: Date = Date(0), type: String = "unknown") {

    var type: String = type

    var sensorType = SensorType.UNKNOWN
    private set
    get() {
        return try {
            SensorType.valueOf(type.toUpperCase())
        }catch (e: IllegalArgumentException){
            SensorType.UNKNOWN
        }
    }
}