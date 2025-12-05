package com.mx.liftechnology.domain.usecase.formativeField


import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain
import com.mx.liftechnology.domain.repository.workType.WorkTypeRepository

/**
 * @file Define el caso de uso para obtener la lista de tipos de evaluación disponibles.
 * @author Pelkidev
 * @version 1.0.0
 */
/**
 * Caso de uso para obtener la lista de tipos de evaluación.
 * Encapsula la lógica de negocio para solicitar los tipos de evaluación desde el repositorio y manejar la respuesta.
 *
 * @property workTypeRepository El repositorio para operaciones relacionadas con tipos de trabajo.
 * @property preference El caso de uso para acceder a las preferencias del usuario (IDs de sesión, etc.).
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListWorkTypeUseCase(
    private val workTypeRepository: WorkTypeRepository,
    private val preference : PreferenceUseCase
) {
    /**
     * Ejecuta el proceso para obtener la lista de tipos de evaluación.
     * Construye la petición, la envía al repositorio y transforma la respuesta en un [ModelResult].
     *
     * @return Un [ModelResult] que contiene la lista de [ResponseGetListWorkType] en caso de éxito,
     * o un estado de error específico en caso de fallo.
     */
    suspend operator fun invoke(): ModelResult<List<WorkTypeDomain>, ModelError> {
        val teacherId = preference.getIdUser() ?: return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        val result = workTypeRepository.getWorkTypeList(teacherId)
        return when (result) {
            is SuccessResult -> {
                if (result.data.isEmpty()) ErrorResult(LocalModelError.EMPTY)
                else result
            }
            is ErrorResult -> result
        }
    }
}