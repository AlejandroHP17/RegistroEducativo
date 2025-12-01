package com.mx.liftechnology.data.repository.student

import com.mx.liftechnology.core.network.api.RequestEditStudent
import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.mapper.StudentMapper.toData
import com.mx.liftechnology.data.model.student.StudentData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

fun interface EditStudentRepository{
    /**
     * Edita un estudiante existente.
     *
     * @param request Los datos de la petición de edición.
     * @param studentId El ID del estudiante a editar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun edit(request: RequestEditStudent, studentId: Int)
            : ModelResult<StudentData, NetworkModelError>
}

class EditStudentRepositoryImpl (
    private val studentApi: StudentApi
) : EditStudentRepository {
    override suspend fun edit(request: RequestEditStudent, studentId: Int)
    : ModelResult<StudentData, NetworkModelError> {
        return studentApi.editStudent(studentId, request).executeOrError { it.toData() }
    }
}