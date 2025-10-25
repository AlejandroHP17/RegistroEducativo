package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterAssignment
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterListAssignmentRepository
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
 * @file Define el caso de uso para registrar una lista de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Interfaz para el caso de uso que registra una lista de asignaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterListAssignmentUseCase {
    /**
     * Ejecuta el proceso de registro de una lista de asignaciones.
     *
     * @return Un [ResultModel] que indica el resultado del registro.
     */
    suspend fun registerListAssignment () :ResultModel<List<String>?, String?>
}

/**
 * Implementación de [RegisterListAssignmentUseCase].
 *
 * @property registerListAssignmentRepository El repositorio para registrar la lista de asignaciones.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListAssignmentUseCaseImp(
    private val registerListAssignmentRepository: RegisterListAssignmentRepository,
    private val preference : PreferenceUseCase
): RegisterListAssignmentUseCase {
    /**
     * {@inheritDoc}
     */
    override suspend fun registerListAssignment():ResultModel<List<String>?, String?> {
        val teacherId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val teacherSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = RequestRegisterAssignment(
            teacherId = teacherId,
            userId = userId,
            teacherSchoolCycleGroupId = teacherSchoolCycleGroupId
        )

        return when(val result =  registerListAssignmentRepository.executeRegisterListAssignment(request)){
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