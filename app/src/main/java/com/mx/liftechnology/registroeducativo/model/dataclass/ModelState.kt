package com.mx.liftechnology.registroeducativo.model.dataclass

/** Sealed class, a generic result
 * @author Pelkidev
 * @date 01/01/24
 * @since 1.0.0
 */

sealed class ModelState<T>
class SuccessState<T>(val result: T) : ModelState<T>()
class ErrorState<T>(val result: Int) : ModelState<T>()
class EmptyState<T>() : ModelState<T>()
