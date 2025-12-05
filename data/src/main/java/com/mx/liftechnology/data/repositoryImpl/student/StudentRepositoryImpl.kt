package com.mx.liftechnology.data.repositoryImpl.student

import com.mx.liftechnology.core.network.api.RequestEditStudent
import com.mx.liftechnology.core.network.api.RequestRegisterStudent
import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.StudentMapper.toEditStudentDomain
import com.mx.liftechnology.data.mapper.StudentMapper.toListStudentDomain
import com.mx.liftechnology.data.mapper.StudentMapper.toStudentDomain
import com.mx.liftechnology.data.mapper.StudentMapper.toStringDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.repository.student.StudentRepository

/**
 * Implementación de [StudentRepository].
 * Se encarga de realizar las llamadas a la API y de gestionar las respuestas de éxito y error
 * para todas las operaciones relacionadas con estudiantes.
 *
 * @property studentApi La llamada a la API para operaciones de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
class StudentRepositoryImpl(
    private val studentApi: StudentApi
) : StudentRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getStudents(
        cycleSchoolId: Int
    ): ModelResult<List<StudentDomain>, NetworkModelError> {
        return safeApiCall(
            apiCall = { studentApi.getListStudents(cycleSchoolId) },
            mapper = { it.toListStudentDomain() }
        )
    }

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
        teacherId: Int,
        schoolCycleId: Int,
        isActive: Boolean
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
            mapper = { it.toStudentDomain() }
        )
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun edit(
        request: RequestEditStudent,
        studentId: Int
    ): ModelResult<StudentDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { studentApi.editStudent(studentId, request) },
            mapper = { it.toEditStudentDomain() }
        )
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun delete(studentId: Int): ModelResult<String, NetworkModelError> {
        return safeApiCall(
            apiCall = { studentApi.deleteStudent(studentId) },
            mapper = { it.toStringDomain() }
        )
    }


}
