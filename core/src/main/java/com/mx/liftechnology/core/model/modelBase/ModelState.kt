package com.mx.liftechnology.core.model.modelBase

/** Sealed class, a generic result
 * @author Pelkidev
 * @since 1.0.0
 * @param S  data type or model for the state of success for example <String>,<EKTStoreModel>
 * @param E  data type or model for the error state  for example <String>,<EKTStoreModel>
 */
sealed class ModelState<S, E>
class SuccessState<S, E>(val result: S) : ModelState<S, E>()
class ErrorState<S, E>(val result: E) : ModelState<S, E>()
class ErrorStateUser<S, E>(val result: E) : ModelState<S, E>()
class EmptyState<S, E>(val result: E) : ModelState<S, E>()
class LoaderState<S, E>(val result: S?) : ModelState<S, E>()