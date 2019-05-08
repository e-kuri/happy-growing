package com.kuri.happygrowing.shared.exceptions

import java.lang.Exception

class InvalidViewModel: Exception {

    constructor(message: String): super(message)

}