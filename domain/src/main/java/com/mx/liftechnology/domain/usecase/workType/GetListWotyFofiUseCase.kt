package com.mx.liftechnology.domain.usecase.workType

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.domain.model.formativeFields.WotyFofiDomain
import com.mx.liftechnology.domain.repository.formativeFields.FormativeFieldRepository

/**
 * Caso de uso para obtener la lista de tipos de trabajo y campos formativos asociados a un ciclo escolar.
 * Encapsula la lógica de negocio para recuperar la relación entre tipos de trabajo y campos formativos
 * basándose en el ciclo escolar seleccionado en las preferencias del usuario.
 *
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 * @property formativeFieldRepository El repositorio para operaciones relacionadas con campos formativos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListWotyFofiUseCase(
    private val preference: PreferenceUseCase,
    private val formativeFieldRepository: FormativeFieldRepository
) {

    /**
     * Ejecuta el proceso de obtención de la lista de tipos de trabajo y campos formativos.
     * Obtiene el ID del ciclo escolar desde las preferencias y recupera la lista asociada.
     *
     * @return Un [ModelResult] que contiene los datos de tipos de trabajo y campos formativos
     * ([WotyFofiDomain]) en caso de éxito, o un estado de error específico en caso de fallo.
     *
     * Posibles errores:
     * - [LocalModelError.USER_INCOMPLETE_DATA] si no hay un ciclo escolar seleccionado en las preferencias
     * - [ModelError] de red si hay problemas de conexión
     * - [ModelError] si no se encuentran datos para el ciclo escolar
     *
     * @example
     * ```
     * val result = getListWotyFofiUseCase()
     * when (result) {
     *     is SuccessResult -> {
     *         val data = result.data
     *         println("Tipos de trabajo y campos formativos: ${data}")
     *     }
     *     is ErrorResult -> println("Error: ${result.error}")
     * }
     * ```
     */
    suspend operator fun invoke(): ModelResult<WotyFofiDomain, ModelError> {
        val schoolCycleId =
            preference.getIdCycleSchool() ?: return ErrorResult(
                LocalModelError.USER_INCOMPLETE_DATA
            )

        return formativeFieldRepository.getListWotyFofi(schoolCycleId)

    }
}