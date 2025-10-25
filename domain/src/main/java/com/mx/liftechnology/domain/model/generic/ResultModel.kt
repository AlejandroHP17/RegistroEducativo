/**
 * @file Define el modelo de estado genérico para gestionar los resultados de las operaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.generic

/**
 * Clase sellada que representa el estado de una operación, que puede ser un éxito o varios tipos de errores.
 * Esta clase genérica se utiliza para manejar las respuestas de los casos de uso y repositorios.
 *
 * @param S El tipo de datos devuelto en una operación exitosa.
 * @param E El tipo de datos devuelto en una operación fallida.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
sealed class ResultDomain<out S, out E: Error>

data class Success<D>(val data: D) : ResultModel<D, Nothing>()
data class Failure<E : Error>(val error: E) : ResultModel<Nothing, E>()


sealed class ResultModel<out S, out E>

/**
 * Representa una operación exitosa.
 * @param result Los datos devueltos por la operación exitosa.
 */
data class SuccessResult<S, E>(val result: S) : ResultModel<S, E>()

/**
 * Representa un error genérico.
 * @param result Los datos del error.
 */
data class ErrorResult<S, E>(val result: E) : ResultModel<S, E>()

/**
 * Representa un error relacionado con la entrada o validación del usuario.
 * @param result Los datos del error, que normalmente contienen un mensaje para el usuario.
 */
data class ErrorUserResult<S, E>(val result: E) : ResultModel<S, E>()

/**
 * Representa un error de autorización (ej: 401 No Autorizado).
 * @param result Los datos del error.
 */
data class ErrorUnauthorizedResult<S, E>(val result: E) : ResultModel<S, E>()
