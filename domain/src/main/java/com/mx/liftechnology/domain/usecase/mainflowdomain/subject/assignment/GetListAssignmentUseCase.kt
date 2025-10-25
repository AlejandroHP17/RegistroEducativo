package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListAssignment
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetListAssignmentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.SuccessResult

/**
 * @file Define el caso de uso para obtener la lista de nombres de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Interfaz para el caso de uso que obtiene la lista de asignaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListAssignmentUseCase {
    /**
     * Ejecuta el proceso para obtener la lista de nombres de asignaciones.
     *
     * @return Un [ResultModel] que contiene una lista de `String` con los nombres de las asignaciones, o un estado de error.
     */
    suspend fun getListAssignment () :ResultModel<List<String>?, String?>
}

/**
 * Implementación de [GetListAssignmentUseCase].
 *
 * @property getListAssignmentRepository El repositorio para obtener la lista de asignaciones.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListAssignmentUseCaseImp(
    private val getListAssignmentRepository: GetListAssignmentRepository,
    private val preference : PreferenceUseCase
): GetListAssignmentUseCase {
    /**
     * {@inheritDoc}
     */
    override suspend fun getListAssignment():ResultModel<List<String>?, String?> {
        val teacherId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val teacherSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = RequestGetListAssignment(
            teacherId = teacherId,
            userId = userId,
            teacherSchoolCycleGroupId = teacherSchoolCycleGroupId
        )

        return when(val result =  getListAssignmentRepository.executeGetListAssignment(request)){
            is ResultSuccess -> SuccessResult(result.data)
            is ResultError -> handleResponse(result.error)
        }
    }

    /**
     * Maneja las respuestas de error del repositorio de asignaciones.
     *
     * @param error El objeto [FailureService] que representa el error.
     * @return Un [ResultModel] que representa el error específico para la capa de dominio/UI.
     */
    private fun handleResponse(error: FailureService): ResultModel<List<String>?, String?> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.NotFound -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_INFO)
            is FailureService.Timeout -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}
