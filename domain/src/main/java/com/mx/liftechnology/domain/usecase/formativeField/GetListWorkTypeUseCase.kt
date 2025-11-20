package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeData
import com.mx.liftechnology.data.repository.formativeField.GetWorkTypeRepository
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalModelError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult

/**
 * @file Define el caso de uso para obtener la lista de tipos de evaluación disponibles.
 * @author Pelkidev
 * @version 1.0.0
 */
/**
 * Caso de uso para obtener la lista de tipos de evaluación.
 * Encapsula la lógica de negocio para solicitar los tipos de evaluación desde el repositorio y manejar la respuesta.
 *
 * @property getWorkTypeRepository El repositorio para obtener los datos de los tipos de evaluación.
 * @property preference El caso de uso para acceder a las preferencias del usuario (IDs de sesión, etc.).
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListWorkTypeUseCase(
    private val getWorkTypeRepository: GetWorkTypeRepository,
    private val preference : PreferenceUseCase
) {
    /**
     * Ejecuta el proceso para obtener la lista de tipos de evaluación.
     * Construye la petición, la envía al repositorio y transforma la respuesta en un [ModelResult].
     *
     * @return Un [com.mx.liftechnology.data.util.ModelResult] que contiene la lista de [com.mx.liftechnology.core.network.apiCall.formativeField.ResponseGetListWorkType] en caso de éxito,
     * o un estado de error específico en caso de fallo.
     */
    suspend operator fun invoke(): ModelResult<List<ModelWorkTypeData>, ModelError> {
        val teacherId = preference.getPreferenceInt(ModelPreference.ID_USER)

        if(teacherId == null) return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        return runCatching { getWorkTypeRepository.executeGetListWorkType(teacherId) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        if (result.data.isEmpty()) ErrorResult(LocalModelError.EMPTY)
                        else SuccessResult(result.data)
                    }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
        )
    }
}