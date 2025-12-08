/**
 * @file Define los códigos de error para la validación de campos de entrada.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.generic

/**
 * Objeto que contiene los códigos de error para la validación de campos de entrada.
 * Centralizar estos códigos ayuda a mantener la consistencia en los mensajes de error.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ModelCodeInputs {
    /** Indica que el formato es correcto. */
    const val ET_CORRECT_FORMAT = "Formato correcto"

    /** Indica que el campo está vacío. */
    const val ET_EMPTY = "El campo no puede estar vacío"

    /** Indica que el spinner está vacío. */
    const val ET_SPINNER_EMPTY: String = "Selecciona un campo"

    /** Indica que el formato es incorrecto. */
    const val ET_MISTAKE_FORMAT: String = "El formato es incorrecto"

    /** Indica que el formato del email es incorrecto. */
    const val ET_USER_FORMAT_MISTAKE = "Formato de correo incorrecto"

    /** Indica que el formato de la contraseña es incorrecto. */
    const val ET_PASS_FORMAT_MISTAKE = "Formato de contraseña incorrecto"

    /** Indica que las contraseñas no coinciden. */
    const val ET_PASS_DIFFERENT_MISTAKE = "Las contraseñas no coinciden"

    /** Indica que el formato de la CURP es incorrecto. */
    const val ET_CURP_FORMAT_MISTAKE = "El formato de la CURP es incorrecto"

    /** Indica que el formato del número de teléfono es incorrecto. */
    const val ET_PHONE_NUMBER_FORMAT_MISTAKE = "El formato del número de teléfono es incorrecto"

    /** Indica que el CCT no fue encontrado. */
    const val ET_NOT_FOUND = "El CCT no se encontró"


    // Spinners
    /** Indica que el spinner no puede estar vacío. */
    const val SP_NOT_OPTION: String = "No puede estar vacío"

    /** Indica que el spinner está vacío */
    const val SP_NOT_JOB: String = "Vacío"

    /** Indica que la suma de porcentajes no es 100. */
    const val SP_NOT = "La suma debe ser 100%"
}