package com.mx.liftechnology.domain.model.generic

/**
 * A sealed class representing the state of an operation, which can be a success or various types of errors.
 * This generic class is used to handle responses from use cases and repositories.
 *
 * @param S The type of data returned on a successful operation.
 * @param E The type of data returned on a failed operation.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
sealed class ModelState<S, E>

/**
 * Represents a successful operation.
 * @param result The data returned from the successful operation.
 */
class SuccessState<S, E>(val result: S) : ModelState<S, E>()

/**
 * Represents a generic error.
 * @param result The error data.
 */
class ErrorState<S, E>(val result: E) : ModelState<S, E>()

/**
 * Represents an error related to user input or validation.
 * @param result The error data, typically containing a message for the user.
 */
class ErrorUserState<S, E>(val result: E) : ModelState<S, E>()

/**
 * Represents an authorization error (e.g., 401 Unauthorized).
 * @param result The error data.
 */
class ErrorUnauthorizedState<S, E>(val result: E) : ModelState<S, E>()