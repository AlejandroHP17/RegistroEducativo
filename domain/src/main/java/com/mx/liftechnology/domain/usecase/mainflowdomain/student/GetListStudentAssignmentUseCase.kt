/**
 * @file Define el caso de uso para obtener la lista de estudiantes para una asignación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListStudent
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.student.GetStudentRepository
import com.mx.liftechnology.data.util.ErrorResult as DataErrorResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult as DataSuccessResult
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.model.student.ModelStudentRegisterAssignmentDomain
import com.mx.liftechnology.domain.model.student.toModelStudentRegisterAssignmentList

/**
 * Caso de uso para obtener la lista de estudiantes para el registro de una nueva asignación.
 * Encapsula la lógica de negocio para solicitar la lista de estudiantes, procesarla y manejar los errores.
 *
 * @property getStudentRepository El repositorio para obtener la lista de estudiantes.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListStudentAssignmentUseCase (
    private val getStudentRepository: GetStudentRepository,
    private val preference: PreferenceUseCase
){
    /**
     * Ejecuta el proceso para obtener la lista de estudiantes de una asignación.
     *
     * @return Un [ResultModel] que contiene la lista de estudiantes formateada para el registro de asignación,
     * o un estado de error si la operación falla.
     */
    suspend operator fun invoke(): ResultModel<List<ModelStudentRegisterAssignmentDomain>?, String> {
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val pecg =
            preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = RequestGetListStudent(
            teacherId = roleId,
            userId = userId,
            teacherSchoolCycleGroupId = pecg
        )

        return runCatching { getStudentRepository.executeGetListStudent(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is DataSuccessResult -> {
                        if (result.data.isNullOrEmpty()) ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
                        else SuccessResult(result.data?.toModelStudentRegisterAssignmentList())
                    }

                    is DataErrorResult -> {
                        handleResponseAssignment(result.error)
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
    private fun handleResponseAssignment(error: NetworkError): ResultModel<List<ModelStudentRegisterAssignmentDomain>?, String> {
        return when (error) {
            NetworkError.BAD_REQUEST -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            NetworkError.UNAUTHORIZED -> ErrorUnauthorizedResult(ModelCodeError.ERROR_UNAUTHORIZED)
            NetworkError.NOT_FOUND -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            NetworkError.TIMEOUT -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}