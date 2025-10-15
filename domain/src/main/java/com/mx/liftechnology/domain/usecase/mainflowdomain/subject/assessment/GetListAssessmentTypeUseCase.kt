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
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

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
     * Construye la petición, la envía al repositorio y transforma la respuesta en un [ModelState].
     *
     * @return Un [ModelState] que contiene la lista de [ResponseGetListAssessmentType] en caso de éxito,
     * o un estado de error específico en caso de fallo.
     */
    suspend operator fun invoke(): ModelState<List<ResponseGetListAssessmentType?>?, String?> {
        val teacherId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val teacherSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = RequestGetListAssessmentType(
            teacherId = teacherId,
            userId = userId,
            teacherSchoolCycleGroupId = teacherSchoolCycleGroupId
        )

        return runCatching { getAssessmentTypeRepository.executeGetListAssessment(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is ResultSuccess -> {
                        result.data?.let {
                            SuccessState(result.data)
                        }?: ErrorState(ModelCodeError.ERROR_UNKNOWN)
                    }

                    is ResultError -> {
                        handleResponse(result.error)
                    }
                }
            },
            onFailure = {ErrorState(ModelCodeError.ERROR_UNKNOWN)}
        )
    }

    /**
     * Maneja las respuestas de error del repositorio, convirtiendo un [FailureService] en un [ModelState] específico.
     *
     * @param error El objeto [FailureService] que representa el error de la capa de datos.
     * @return Un [ModelState] que representa el error específico para la capa de dominio/UI.
     */
    private fun handleResponse(error: FailureService): ModelState<List<ResponseGetListAssessmentType?>?, String?> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_INFO)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}