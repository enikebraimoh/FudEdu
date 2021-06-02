package com.enike.fudedu.utils

interface DataState {
    fun loading()
    fun error(error: String)
    fun <T> success(message: T? = null)
    fun waiting()
}

fun randomNumberGen(): Int {
    val number = 1..9
    return number.random()
}