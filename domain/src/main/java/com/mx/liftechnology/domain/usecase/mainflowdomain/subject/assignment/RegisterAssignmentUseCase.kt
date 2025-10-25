package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterJobStudent
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestStudentJobs
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseStudentJobs
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterAssignmentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.SuccessResult

/**
 * @file Define el caso de uso para registrar una nueva asignación (trabajo) de un estudiante.
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Caso de uso para registrar una nueva asignación.
 * Encapsula la lógica de negocio para construir la petición de registro de una asignación y manejar la respuesta.
 *
 * @property registerAssignmentRepository El repositorio para las operaciones de registro de asignaciones.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterAssignmentUseCase (
    private val registerAssignmentRepository: RegisterAssignmentRepository,
    private val preference: PreferenceUseCase
){

    /**
     * Ejecuta el proceso de registro de una asignación.
     *
     * @param nameJob El nombre del trabajo o asignación.
     * @param typeJob El tipo de trabajo (ID).
     * @param date La fecha de la asignación.
     * @param studentListUI La lista de estudiantes y sus datos relacionados con el trabajo.
     * @return Un [ResultModel] que indica el resultado de la operación de registro.
     */
    suspend operator fun invoke(nameJob: String, typeJob: Int, date: String, studentListUI:  List<RequestStudentJobs>): ResultModel<List<ResponseStudentJobs?>?, String> {
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)
        val partialCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP)

        val request = RequestRegisterJobStudent(
            description = nameJob,
            date = date,
            number = 1,
            typeJobPecgId = typeJob,
            fieldPecgPercentId = typeJob,
            userId = userId,
            teacherId = roleId,
            teacherSchoolCycleGroupId = profSchoolCycleGroupId,
            partialCycleGroupId = partialCycleGroupId?:1,
            dayPartialCycleGroupId = 1,
            studentJobs = studentListUI

        )

        return runCatching { registerAssignmentRepository.RegisterAssignment(request) }.fold(
            onSuccess = { result ->
                when (result){
                    is ResultSuccess -> {
                        result.data?.let {
                            SuccessResult(result.data)
                        }?: ErrorResult(ModelCodeError.ERROR_CRITICAL)
                    }
                    is ResultError -> {
                        handleResponse(result.error)
                    }

                }
            },
            onFailure = { ErrorResult(ModelCodeError.ERROR_CRITICAL) }
        )
    }

    /**
     * Maneja las respuestas de error del repositorio de registro de asignaciones.
     *
     * @param error El objeto [FailureService] que representa el error.
     * @return Un [ResultModel] que representa el error específico para la capa de dominio/UI.
     */
    private fun handleResponse(error: FailureService): ResultModel<List<ResponseStudentJobs?>?, String> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_LOGIN)
            is FailureService.Unauthorized -> ErrorResult(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_LOGIN)
            is FailureService.Timeout -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}