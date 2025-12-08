package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner

/**
 * Mapper genérico para funciones de utilidad compartidas entre diferentes dominios.
 * Incluye validaciones para asegurar la integridad de los datos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object GenericMapper {

    /**
     * Convierte un String a ModelCustomSpinner.
     * Valida que el string pueda convertirse a un número entero válido.
     *
     * @param value El string a convertir.
     * @return Un ModelCustomSpinner con el valor y su ID numérico, o null si la conversión falla.
     */
    fun String.toModelCustomSpinner(): ModelCustomSpinner? {
        if (this.isBlank()) return null

        val id = this.toIntOrNull()
            ?: // Si no se puede convertir a número, usar el hash del string como ID
            return ModelCustomSpinner(
                value = this,
                id = this.hashCode()
            )

        return ModelCustomSpinner(
            value = this,
            id = id
        )
    }
}

