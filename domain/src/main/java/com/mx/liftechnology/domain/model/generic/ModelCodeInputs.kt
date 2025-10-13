package com.mx.liftechnology.domain.model.generic

/**
 * Data class holding constants for input field validation messages.
 *
 * @property value A string value, the purpose of which should be clarified.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelCodeInputs(val value: String) {
    companion object {
        /** Message for a correctly formatted input. */
        const val ET_CORRECT_FORMAT: String = "Formato correcto"

        /** Message for an empty input field. */
        const val ET_EMPTY: String = "El campo no puede estar vacío"

        /** Message for an empty spinner selection. */
        const val ET_SPINNER_EMPTY: String = "Selecciona un campo"

        /** Generic message for incorrect format. */
        const val ET_MISTAKE_FORMAT: String = "El formato es incorrecto"

        /** Message for incorrect user or email format. */
        const val ET_USER_FORMAT_MISTAKE: String = "Formato de usuario o correo incorrecto"

        /** Message for incorrect password format. */
        const val ET_PASS_FORMAT_MISTAKE: String = "Formato de contraseña incorrecta"

        /** Message for mismatching passwords. */
        const val ET_PASS_DIFFERENT_MISTAKE: String = "Las constraseñas no coinciden"

        /** Message for incorrect CURP format. */
        const val ET_CURP_FORMAT_MISTAKE: String = "El formato de CURP es incorrecto"

        /** Message for incorrect phone number format. */
        const val ET_PHONE_NUMBER_FORMAT_MISTAKE: String = "El formato de Numero telefónico es incorrecto"

        /** Message for a value not found. */
        const val ET_NOT_FOUND: String = "Dato no encontrado"

        // Spinners
        /** Message for an empty spinner selection. */
        const val SP_NOT_OPTION: String = "No puede estar vacío"

        /** Message for an empty job title. */
        const val SP_NOT_JOB: String = "Vacío"

        /** Message indicating that the sum of percentages must be 100. */
        const val SP_NOT: String = "La suma debe ser 100%"
    }
}