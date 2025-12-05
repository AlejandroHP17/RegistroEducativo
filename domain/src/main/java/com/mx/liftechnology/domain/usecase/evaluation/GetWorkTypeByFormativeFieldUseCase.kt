package com.mx.liftechnology.domain.usecase.evaluation

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.repository.workType.WorkTypeRepository

/**
 * Caso de uso para obtener los tipos de trabajo asociados a un campo formativo específico.
 * Encapsula la lógica de negocio para recuperar los tipos de trabajo basándose en el campo formativo
 * seleccionado en las preferencias del usuario.
 *
 * @property workTypeRepository El repositorio para operaciones relacionadas con tipos de trabajo.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetWorkTypeByFormativeFieldUseCase (
    private val workTypeRepository: WorkTypeRepository,
    private val preference : PreferenceUseCase
){
    /**
     * Ejecuta el proceso de obtención de tipos de trabajo por campo formativo.
     * Obtiene el ID del campo formativo desde las preferencias y recupera los tipos de trabajo asociados.
     *
     * @return Un [ModelResult] que contiene los tipos de trabajo asociados al campo formativo
     * ([WorkTypeByFormativeFieldDomain]) en caso de éxito, o un estado de error específico en caso de fallo.
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
    suspend operator fun invoke(): ModelResult<WorkTypeByFormativeFieldDomain, ModelError> {
        val formativeFieldId =
            preference.getIdFormativeField() ?: return ErrorResult(
                LocalModelError.USER_INCOMPLETE_DATA
            )

        return workTypeRepository.getWorkTypeByFormativeField(formativeFieldId = formativeFieldId)
    }
}