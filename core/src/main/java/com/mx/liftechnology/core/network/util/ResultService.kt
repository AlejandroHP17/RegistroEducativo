package com.mx.liftechnology.core.network.util

sealed class ResultService<out S, out E>
data class ResultSuccess<S>(val data: S) : ResultService<S, Nothing>()
data class ResultError<E>(val error: E) : ResultService<Nothing, E>()