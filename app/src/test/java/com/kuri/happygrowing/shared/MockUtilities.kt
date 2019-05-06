package com.kuri.happygrowing.shared

import org.mockito.Mockito

inline fun <reified T: Any> mock() = Mockito.mock(T::class.java)

fun <T> uninitialized(): T = null as T

fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}