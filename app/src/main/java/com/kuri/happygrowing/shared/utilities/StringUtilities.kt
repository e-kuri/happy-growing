package com.kuri.happygrowing.shared.utilities

import java.lang.NumberFormatException

fun String.isFloat() =
        try {
            this.toFloat()
            true
        } catch (e: NumberFormatException) {
            false
        }