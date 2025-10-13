package com.mx.liftechnology.domain.model.generic

/**
 * Object containing constants for voice command recognition.
 * These constants represent the keywords used to identify specific fields during voice input.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ModelVoiceConstants {
    /** Keyword for the user's first name. */
    const val NAME = "nombre"

    /** Keyword for the user's paternal last name. */
    const val LAST_NAME = "apellido paterno"

    /** Keyword for the user's maternal last name. */
    const val SECOND_LAST_NAME = "apellido materno"

    /** Keyword for the user's birth date. */
    const val BIRTHDAY = "fecha de nacimiento"

    /** Keyword for the user's contact phone number. */
    const val PHONE_NUMBER = "número de contacto"

    /** Keyword for the user's CURP (Clave Única de Registro de Población). */
    const val CURP = "CURP"
}
