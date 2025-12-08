package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.domain.repository.formativeFields.FormativeFieldRepository


/**
 * Caso de uso para eliminar un campo formativo del sistema.
 * Encapsula la lógica de negocio para eliminar un campo formativo mediante su identificador.
 *
 * @property formativeFieldRepository El repositorio para operaciones relacionadas con campos formativos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class DeleteFormativeFieldsUseCase(
    private val formativeFieldRepository: FormativeFieldRepository
) {

    /**
     * Ejecuta el proceso de eliminación de un campo formativo.
     *
     * @param fieldId El identificador único del campo formativo a eliminar.
     * @return Un [ModelResult] que contiene un mensaje de confirmación en caso de éxito,
     * o un estado de error específico en caso de fallo.
     *
     * Posibles errores:
     * - [ModelError] de red si hay problemas de conexión
     * - [ModelError] de validación si el campo formativo no existe o no se puede eliminar
     *
     * @example
     * ```
     * val result = deleteFormativeFieldsUseCase(456)
     * when (result) {
     *     is SuccessResult -> println("Campo formativo eliminado: ${result.data}")
     *     is ErrorResult -> println("Error: ${result.error}")
     * }
     * ```
     */
    suspend operator fun invoke (fieldId: Int): ModelResult<String, ModelError> {
        return formativeFieldRepository.delete(fieldId)
    }
}