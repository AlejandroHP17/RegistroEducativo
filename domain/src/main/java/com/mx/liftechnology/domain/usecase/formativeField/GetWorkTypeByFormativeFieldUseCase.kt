package com.mx.liftechnology.domain.usecase.formativeField


import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeByFormativeField
import com.mx.liftechnology.data.repository.formativeField.GetWorkTypeByFormativeFieldsRepository
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalModelError
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ModelResult

/**
 * Caso de uso para obtener los tipos de trabajo asociados a un campo formativo específico.
 * Encapsula la lógica de negocio para recuperar los tipos de trabajo basándose en el campo formativo
 * seleccionado en las preferencias del usuario.
 *
 * @property getWorkTypeByFormativeFieldsRepository El repositorio para obtener los tipos de trabajo por campo formativo.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetWorkTypeByFormativeFieldUseCase (
    private val getWorkTypeByFormativeFieldsRepository: GetWorkTypeByFormativeFieldsRepository,
    private val preference : PreferenceUseCase
){
    /**
     * Ejecuta el proceso de obtención de tipos de trabajo por campo formativo.
     * Obtiene el ID del campo formativo desde las preferencias y recupera los tipos de trabajo asociados.
     *
     * @return Un [ModelResult] que contiene los tipos de trabajo asociados al campo formativo
     * ([ModelWorkTypeByFormativeField]) en caso de éxito, o un estado de error específico en caso de fallo.
     *
     * Posibles errores:
     * - [LocalModelError.USER_INCOMPLETE_DATA] si no hay un campo formativo seleccionado en las preferencias
     * - [ModelError] de red si hay problemas de conexión
     * - [ModelError] si no se encuentran tipos de trabajo para el campo formativo
     *
     * @example
     * ```
     * val result = getWorkTypeByFormativeFieldUseCase()
     * when (result) {
     *     is SuccessResult -> {
     *         val workTypes = result.data
     *         println("Tipos de trabajo encontrados: ${workTypes}")
     *     }
     *     is ErrorResult -> println("Error: ${result.error}")
     * }
     * ```
     */
    suspend operator fun invoke(): ModelResult<ModelWorkTypeByFormativeField, ModelError> {
        val formativeFieldId =
            preference.getIdFormativeField() ?: return ErrorResult(
                LocalModelError.USER_INCOMPLETE_DATA
            )

        return getWorkTypeByFormativeFieldsRepository.getByFormativeField(formativeFieldId = formativeFieldId)
    }
}