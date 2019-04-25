package com.kuri.happygrowing.stats.model

import java.util.*

enum class SensorType {
    TEMPERATURE,
    HUMIDITY
}

class Measurement(val value: Float, val date: Date, val stringType: String) {
    val type: SensorType = SensorType.valueOf(stringType.toUpperCase())
}