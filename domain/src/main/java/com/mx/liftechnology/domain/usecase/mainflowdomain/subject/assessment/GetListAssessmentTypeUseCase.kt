/**
 * @file Define el caso de uso para obtener la lista de tipos de evaluación disponibles.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assessment

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListAssessmentType
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assessment.GetAssessmentTypeRepository
import com.mx.liftechnology.data.util.ErrorResult as DataErrorResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult as DataSuccessResult
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.SuccessResult

/**
 * Caso de uso para obtener la lista de tipos de evaluación.
 * Encapsula la lógica de negocio para solicitar los tipos de evaluación desde el repositorio y manejar la respuesta.
 *
 * @property getAssessmentTypeRepository El repositorio para obtener los datos de los tipos de evaluación.
 * @property preference El caso de uso para acceder a las preferencias del usuario (IDs de sesión, etc.).
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListAssessmentTypeUseCase(
    private val getAssessmentTypeRepository: GetAssessmentTypeRepository,
    private val preference : PreferenceUseCase
) {
    /**
     * Ejecuta el proceso para obtener la lista de tipos de evaluación.
     * Construye la petición, la envía al repositorio y transforma la respuesta en un [ResultModel].
     *
     * @return Un [ResultModel] que contiene la lista de [ResponseGetListAssessmentType] en caso de éxito,
     * o un estado de error específico en caso de fallo.
     */
    suspend operator fun invoke(): ResultModel<List<ResponseGetListAssessmentType?>?, String?> {
        val teacherId = preference.getPreferenceInt(ModelPreference.ID_USER_LEVEL)
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val teacherSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        val request = RequestGetListAssessmentType(
            teacherId = teacherId,
            userId = userId,
            teacherSchoolCycleGroupId = teacherSchoolCycleGroupId
        )

        return runCatching { getAssessmentTypeRepository.executeGetListAssessment(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is DataSuccessResult -> {
                        result.data?.let {
                            SuccessResult(result.data)
                        }?: ErrorResult(ModelCodeError.ERROR_UNKNOWN)
                    }

                    is DataErrorResult -> {
                        handleResponse(result.error)
                    }
                }
            },
            onFailure = {ErrorResult(ModelCodeError.ERROR_UNKNOWN)}
        )
    }

    /**
     * Maneja las respuestas de error del repositorio, convirtiendo un [NetworkError] en un [ResultModel] específico.
     *
     * @param error El objeto [NetworkError] que representa el error de la capa de datos.
     * @return Un [ResultModel] que representa el error específico para la capa de dominio/UI.
     */
    private fun handleResponse(error: NetworkError): ResultModel<List<ResponseGetListAssessmentType?>?, String?> {
        return when(error) {
            NetworkError.BAD_REQUEST -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            NetworkError.UNAUTHORIZED -> ErrorUnauthorizedResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            NetworkError.NOT_FOUND -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_INFO)
            NetworkError.TIMEOUT -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}