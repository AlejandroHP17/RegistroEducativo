package com.mx.liftechnology.core.util

/** Sealed class, a generic result
 * @author Pelkidev
 * @since 1.0.0
 */

sealed class ModelState<T>
class SuccessState<T>(val result: T) : ModelState<T>()
class ErrorState<T>(val result: Int) : ModelState<T>()
class EmptyState<T>() : ModelState<T>()
