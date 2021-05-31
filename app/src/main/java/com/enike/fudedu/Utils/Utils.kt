package com.enike.fudedu.Utils

interface DataState {
    fun loading()
    fun error(error: String)
    fun success(message: String)
    fun waiting()
}

fun randomNumberGen(): Int {
    val number = 1..9
    return number.random()
}