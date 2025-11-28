package com.mx.liftechnology.domain.model.generic

import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ModelResult

/**
 * Modelo que encapsula el resultado de una validación de formulario junto con el resultado de la operación.
 * Permite que los Use Cases combinen validación + operación mientras proporcionan información detallada
 * para que el ViewModel pueda actualizar la UI con los estados de validación individuales.
 *
 * @param T El tipo de dato que retorna la operación en caso de éxito.
 * @property validationStates Los estados de validación de cada campo del formulario.
 * @property operationResult El resultado de la operación (solo presente si todas las validaciones pasaron).
 * @property isValid Indica si todas las validaciones pasaron.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelValidationResult<T>(
    val validationStates: Map<String, ModelStateOutFieldText>,
    val operationResult: ModelResult<T, ModelError>? = null,
    val isValid: Boolean
) {
    companion object {
        /**
         * Crea un resultado de validación con errores (validación fallida).
         */
        fun <T> invalid(validationStates: Map<String, ModelStateOutFieldText>): ModelValidationResult<T> {
            return ModelValidationResult(
                validationStates = validationStates,
                operationResult = null,
                isValid = false
            )
        }

        /**
         * Crea un resultado de validación exitoso con el resultado de la operación.
         */
        fun <T> valid(
            validationStates: Map<String, ModelStateOutFieldText>,
            operationResult: ModelResult<T, ModelError>
        ): ModelValidationResult<T> {
            return ModelValidationResult(
                validationStates = validationStates,
                operationResult = operationResult,
                isValid = true
            )
        }
    }
}

