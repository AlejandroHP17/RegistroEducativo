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
sealed class ModelState<S, E>

/**
 * Representa una operación exitosa.
 * @param result Los datos devueltos por la operación exitosa.
 */
class SuccessState<S, E>(val result: S) : ModelState<S, E>()

/**
 * Representa un error genérico.
 * @param result Los datos del error.
 */
class ErrorState<S, E>(val result: E) : ModelState<S, E>()

/**
 * Representa un error relacionado con la entrada o validación del usuario.
 * @param result Los datos del error, que normalmente contienen un mensaje para el usuario.
 */
class ErrorUserState<S, E>(val result: E) : ModelState<S, E>()

/**
 * Representa un error de autorización (ej: 401 No Autorizado).
 * @param result Los datos del error.
 */
class ErrorUnauthorizedState<S, E>(val result: E) : ModelState<S, E>()
