/**
 * @file Define el caso de uso para registrar un nuevo estudiante.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterStudent
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.student.RegisterStudentRepository
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
 * Caso de uso para registrar un único estudiante.
 * Encapsula la lógica de negocio para construir la petición de registro y manejar la respuesta del repositorio.
 *
 * @property crudStudentRepository El repositorio para las operaciones CRUD de estudiantes.
 * @property preference El caso de uso para la gestión de las preferencias de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterOneStudentUseCase(
    private val crudStudentRepository: RegisterStudentRepository,
    private val preference: PreferenceUseCase
) {

    /**
     * Ejecuta el proceso de registro de un estudiante.
     *
     * @param name El nombre del estudiante.
     * @param lastName El apellido paterno del estudiante.
     * @param secondLastName El apellido materno del estudiante.
     * @param curp La CURP del estudiante.
     * @param birthday La fecha de nacimiento del estudiante.
     * @param phoneNumber El número de teléfono del estudiante.
     * @return Un [ResultModel] que indica el resultado de la operación de registro.
     */
    suspend operator fun invoke(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String
    ): ResultModel<List<String?>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val pecg= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = RequestRegisterStudent(
            name = name,
            lastName = lastName,
            secondLastName = secondLastName,
            curp = curp,
            birthday = birthday,
            phoneNumber = phoneNumber,
            teacherId = roleId,
            userId = userId,
            teacherSchoolCycleGroupId = pecg
        )

        return runCatching { crudStudentRepository.executeRegisterOneStudent(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is DataSuccessResult -> {
                        SuccessResult(result.data)
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
     * Maneja las respuestas de error del repositorio de registro de estudiantes.
     *
     * @param error El objeto [NetworkError] que representa el error.
     * @return Un [ResultModel] que representa el error específico para la capa de dominio/UI.
     */
    private fun handleResponse(error: NetworkError): ResultModel<List<String?>?, String> {
        return when (error) {
            NetworkError.BAD_REQUEST -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            NetworkError.UNAUTHORIZED -> ErrorUnauthorizedResult(ModelCodeError.ERROR_UNAUTHORIZED)
            NetworkError.NOT_FOUND -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            NetworkError.TIMEOUT -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}