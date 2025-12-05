package com.mx.liftechnology.data.repositoryImpl.student

import com.mx.liftechnology.core.network.api.RequestEditStudent
import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.StudentMapper.toEditStudentDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.repository.student.EditStudentRepository


class EditStudentRepositoryImpl (
    private val studentApi: StudentApi
) : EditStudentRepository {
    override suspend fun edit(request: RequestEditStudent, studentId: Int)
    : ModelResult<StudentDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { studentApi.editStudent(studentId, request) },
            mapper = { it.toEditStudentDomain() }
        )
    }
}