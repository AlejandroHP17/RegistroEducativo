/**
 * @file Define el caso de uso para obtener la lista de tipos de evaluación disponibles.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.workType


import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListWorkType
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.ModelWorkTypeData
import com.mx.liftechnology.data.repository.flowMain.formativeFields.workType.GetWorkTypeRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult


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
     * Construye la petición, la envía al repositorio y transforma la respuesta en un [ResultModel].
     *
     * @return Un [ModelResult] que contiene la lista de [ResponseGetListWorkType] en caso de éxito,
     * o un estado de error específico en caso de fallo.
     */
    suspend operator fun invoke(): ModelResult<List<ModelWorkTypeData>, Error> {
        val teacherId = preference.getPreferenceInt(ModelPreference.ID_USER)

        if(teacherId == null) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
        )

        return runCatching { getWorkTypeRepository.executeGetListWorkType(teacherId) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        if (result.data.isNullOrEmpty()) ErrorResult(LocalError.EMPTY)
                        else SuccessResult(result.data)
                    }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkError.UNKNOWN)}
        )
    }
}