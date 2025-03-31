package com.mx.liftechnology.domain.model.generic

data class ModelCodeInputs(val value: String) {
    companion object {
        const val ET_CORRECT_FORMAT: String = "Formato correcto"

        const val ET_EMPTY: String = "El campo no puede estar vacío"
        const val ET_SPINNER_EMPTY: String = "Selecciona un campo"
        const val ET_MISTAKE_FORMAT: String = "El formato es incorrecto"

        const val ET_USER_FORMAT_MISTAKE: String = "Formato de usuario o correo incorrecto"
        const val ET_PASS_FORMAT_MISTAKE: String = "Formato de contraseña incorrecta"
        const val ET_PASS_DIFFERENT_MISTAKE: String = "Las constraseñas no coinciden"
        const val ET_CURP_FORMAT_MISTAKE: String = "El formato de CURP es incorrecto"
        const val ET_PHONE_NUMBER_FORMAT_MISTAKE: String = "El formato de Numero telefónico es incorrecto"
        const val ET_NOT_FOUND: String = "Dato no encontrado"

        // Spinners
        const val SP_NOT_OPTION: String = "No ha seleccionado un valor válido del spinner"
        const val SP_NOT_JOB: String = "El porcentaje total debe ser 100%"
    }
}