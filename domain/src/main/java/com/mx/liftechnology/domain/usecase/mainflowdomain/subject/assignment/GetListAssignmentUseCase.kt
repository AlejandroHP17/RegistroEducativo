package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListAssignment
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetListAssignmentRepository
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
     * @return Un [ModelState] que contiene una lista de `String` con los nombres de las asignaciones, o un estado de error.
     */
    suspend fun getListAssignment () :ModelState<List<String>?, String?>
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
    override suspend fun getListAssignment():ModelState<List<String>?, String?> {
        val teacherId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val teacherSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = RequestGetListAssignment(
            teacherId = teacherId,
            userId = userId,
            teacherSchoolCycleGroupId = teacherSchoolCycleGroupId
        )

        return when(val result =  getListAssignmentRepository.executeGetListAssignment(request)){
            is ResultSuccess -> SuccessState(result.data)
            is ResultError -> handleResponse(result.error)
        }
    }

    /**
     * Maneja las respuestas de error del repositorio de asignaciones.
     *
     * @param error El objeto [FailureService] que representa el error.
     * @return Un [ModelState] que representa el error específico para la capa de dominio/UI.
     */
    private fun handleResponse(error: FailureService): ModelState<List<String>?, String?> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_INFO)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}
