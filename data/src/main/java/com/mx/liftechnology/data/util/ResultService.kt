package com.mx.liftechnology.data.util

/**
 * A sealed class representing the result of a service call, which can be either a success or an error.
 *
 * @param S The type of data returned on a successful operation.
 * @param E The type of data returned on a failed operation.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
sealed class ResultService<out S, out E>

/**
 * Represents a successful service call.
 * @param data The data returned from the successful operation.
 */
data class ResultSuccess<S>(val data: S) : ResultService<S, Nothing>()

/**
 * Represents a failed service call.
 * @param error The error data.
 */
data class ResultError<E>(val error: E) : ResultService<Nothing, E>()