package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.repository.student.RegisterStudentRepository

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
class RegisterStudentUseCase(
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
    ): ModelResult<StudentDomain?,
            ModelError> {
        val teacherId = preference.getIdUser()
        val cycleSchoolId = preference.getIdCycleSchool()

        if(teacherId == null || cycleSchoolId == null ) return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )
        val result = crudStudentRepository.register(
            name = name.trim(),
            lastName = lastName.trim(),
            secondLastName = secondLastName.trim(),
            curp = curp,
            birthday = birthday,
            phoneNumber = phoneNumber.trim(),
            teacherId = teacherId,
            schoolCycleId = cycleSchoolId,
            isActive = true
        )
        return when (result) {
            is SuccessResult -> {
                SuccessResult(result.data)
            }
            is ErrorResult -> result
        }
    }
}