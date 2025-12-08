/**
 * @file Define el modelo de datos para el estado de un campo de texto con validación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.generic

/**
 * Modelo de datos que representa el estado de un campo de texto en la UI.
 * Incluye el valor del texto, si hay un error de validación y el mensaje de error correspondiente.
 *
 * @property valueText El texto actual del campo.
 * @property isError `true` si el campo tiene un error de validación, `false` en caso contrario.
 * @property errorMessage El mensaje de error a mostrar si `isError` es `true`.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelStateOutFieldText(
    val valueText: String = "",
    val isError: Boolean = false,
    val errorMessage: String = ""
)