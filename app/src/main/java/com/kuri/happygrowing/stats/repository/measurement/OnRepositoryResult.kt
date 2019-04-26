package com.kuri.happygrowing.stats.repository.measurement

interface OnRepositoryResult<T> {

    fun onSuccessResult(result: T)

    fun onError(e: Exception)

}