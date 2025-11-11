/**
 * @file Define el caso de uso para registrar un nuevo estudiante.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.core.network.apiCall.student.RequestRegisterStudent
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.student.ModelStudentData
import com.mx.liftechnology.data.repository.student.RegisterStudentRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult

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
     * @return Un [ModelResult] que indica el resultado de la operación de registro.
     */
    suspend operator fun invoke(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String
    ): ModelResult<ModelStudentData?, Error> {
        val teacherId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val cycleSchoolId = preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        if(teacherId == null || cycleSchoolId == null ) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
        )

        val request = RequestRegisterStudent(
            name = name,
            lastName = lastName,
            secondLastName = secondLastName,
            curp = curp,
            birthday = birthday,
            phoneNumber = phoneNumber,
            teacherId = teacherId,
            schoolCycleId = cycleSchoolId,
            isActive = true
        )

        return runCatching { crudStudentRepository.executeRegisterOneStudent(request) }.fold(
            onSuccess = { result ->
                when(result){
                    is SuccessResult -> {
                        SuccessResult(result.data)
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