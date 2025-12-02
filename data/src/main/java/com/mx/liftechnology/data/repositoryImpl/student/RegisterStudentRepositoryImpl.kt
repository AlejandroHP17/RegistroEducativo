/**
 * @file Define el repositorio para la funcionalidad de registro de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.student

import com.mx.liftechnology.core.network.api.RequestRegisterStudent
import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.mapper.StudentMapper.toData
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.repository.student.RegisterStudentRepository

/**
 * Implementación de [RegisterStudentRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerStudentApiCall La llamada a la API para el registro de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterStudentRepositoryImpl(
    private val studentApi: StudentApi,
) : RegisterStudentRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun register(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String,
        teacherId : Int,
        schoolCycleId : Int,
        isActive : Boolean
    ): ModelResult<StudentDomain?, NetworkModelError> {
        val request = RequestRegisterStudent(
            name = name,
            lastName = lastName,
            secondLastName = secondLastName,
            curp = curp,
            birthday = birthday,
            phoneNumber = phoneNumber,
            teacherId = teacherId,
            schoolCycleId = schoolCycleId,
            isActive = true
        )
        return safeApiCall(
            apiCall = { studentApi.registerStudent(request) },
            mapper = { it.toData() }
        )
    }
}