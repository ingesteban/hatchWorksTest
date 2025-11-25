package dev.esteban.network

interface Mapper<T, R> {
    fun apply(input: T): R
}
