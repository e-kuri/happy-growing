package com.kuri.happygrowing.shared

import java.lang.Exception

class InvalidTypeException : Exception {

    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)

}