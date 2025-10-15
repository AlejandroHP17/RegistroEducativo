/**
 * @file Define una clase sellada para encapsular los resultados de las operaciones de la capa de datos.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.util

/**
 * Clase sellada que representa el resultado de una operación de servicio, que puede ser un éxito o un error.
 * Este enfoque permite un manejo de errores más robusto y explícito.
 *
 * @param S El tipo de datos devuelto en una operación exitosa.
 * @param E El tipo de datos devuelto en una operación fallida.
 * @author Pelkidev
 * @version 1.0.0
 */
sealed class ResultService<out S, out E>

/**
 * Representa una llamada de servicio exitosa.
 * @param data Los datos devueltos por la operación exitosa.
 */
data class ResultSuccess<S>(val data: S) : ResultService<S, Nothing>()

/**
 * Representa una llamada de servicio fallida.
 * @param error Los datos del error.
 */
data class ResultError<E>(val error: E) : ResultService<Nothing, E>()