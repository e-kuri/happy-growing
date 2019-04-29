package com.kuri.happygrowing.shared.callback

interface OnResultCallback<T> {

    fun onSuccessResult(result: T)

    fun onError(e: Exception)

}