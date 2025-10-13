package com.mx.liftechnology.domain.model.generic

/**
 * Object that contains regular expressions for input validation across the application.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ModelRegex {
    /** Regex for simple text, allowing alphanumeric characters and spaces. */
    val SIMPLE_TEXT = Regex("^[A-ZÁÉÍÓÚÑa-z0-9áéíóúñ ]+$")

    /** Regex for more complex text, allowing alphanumeric characters, spaces, and some punctuation. */
    val COMPLEX_TEXT = Regex("^[A-ZÁÉÍÓÚÑa-z0-9áéíóúñ ,.\\-/]+$")

    /** Regex for validating a Mexican CURP (Clave Única de Registro de Población). */
    val CURP = Regex("""^[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z\\d]\\d$""")

    /** Regex for validating a 10-digit phone number. */
    val PHONE_NUMBER = Regex("""^\\d{10}$""")

    /** Regex for validating a standard email address format. */
    val EMAIL = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    /** Regex for password validation. Requires at least one lowercase letter, one uppercase letter, one digit, and a minimum of 8 characters. */
    val PASS = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")

    /** Regex for CCT (Clave de Centro de Trabajo) validation. */
    val CCT = Regex("^[A-ZÑ0-9&]$")

    /** Regex for validating a score, allowing integers from 0-10 and decimals. */
    val SCORE = Regex("^(10([.,]0)?|10[.,]?|[0-9]([.,]\\d?)?)$")
}
