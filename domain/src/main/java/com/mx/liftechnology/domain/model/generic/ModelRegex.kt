/**
 * @file Centraliza todas las expresiones regulares utilizadas en la aplicación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.generic

/**
 * Objeto que contiene las expresiones regulares para la validación de campos.
 * El uso de este objeto asegura que las validaciones sean consistentes en toda la aplicación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ModelRegex {
    /** Expresión regular para validar un nombre de usuario (formato de email). */
    val EMAIL = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    /** Expresión regular para validar una contraseña (al menos una mayúscula, una minúscula, un número y 8 caracteres). */
    val PASS = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")

    /** Expresión regular para validar una CURP. */
    val CURP = Regex("^[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]{2}$")

    /** Expresión regular para validar un número de teléfono de 10 dígitos. */
    val PHONE_NUMBER = Regex("^\\d{10}$")

    /** Expresión regular para validar una calificación (números y un punto decimal opcional). */
    val SCORE = Regex("^[0-9]*\\.?[0-9]*$")

    /** Expresión regular para texto simple (letras y espacios). */
    val SIMPLE_TEXT = Regex("^[A-Za-z \\-áéíóúÁÉÍÓÚñÑ]+$")

    /** Expresión regular para texto complejo (letras, números, espacios y caracteres comunes). */
    val COMPLEX_TEXT = Regex("^[A-Za-z0-9 .,_\\-áéíóúÁÉÍÓÚñÑ]+$")

    /** Expresión regular para texto simple con numeros. */
    val TEXT_WITH_NUMBERS = Regex("^[a-zA-Z0-9ñÑ]+$")

    /** Expresión regular para texto especial. */
    val SPECIAL_TEXT = Regex("[a-zA-Z0-9ñÑ@!#$%&'*+\\-/=?^_`{|}~.,]+")
}