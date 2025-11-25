package dev.esteban.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.startFlow(loader: T): Flow<T> = this.onStart { emit(loader) }

fun <T> Flow<T>.onError(mapper: Mapper<Throwable, T>): Flow<T> = this.catch { emit(mapper.apply(it)) }

fun <T, R> Flow<T>.mapper(converter: Mapper<T, R>): Flow<R> = this.map { converter.apply(it) }
