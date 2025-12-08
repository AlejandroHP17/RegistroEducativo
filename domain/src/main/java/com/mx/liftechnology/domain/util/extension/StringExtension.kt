/**
 * @file Proporciona funciones de extensión para la clase String, facilitando la conversión a modelos de estado.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.util.extension

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Convierte una cadena de texto en un [ModelStateOutFieldText].
 *
 * @param isError Indica si el estado representa un error.
 * @param errorMessage El mensaje de error a mostrar (si `isError` es `true`).
 * @return Una instancia de [ModelStateOutFieldText] con los valores proporcionados.
 */
fun String?.stringToModelStateOutFieldText(isError: Boolean = false, errorMessage: String = "") = ModelStateOutFieldText(
    valueText = this ?: "",
    isError = isError,
    errorMessage = errorMessage
)