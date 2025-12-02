package com.mx.liftechnology.domain.repository.student

import com.mx.liftechnology.core.network.api.RequestEditStudent
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.student.StudentDomain

fun interface EditStudentRepository{
    /**
     * Edita un estudiante existente.
     *
     * @param request Los datos de la petición de edición.
     * @param studentId El ID del estudiante a editar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun edit(request: RequestEditStudent, studentId: Int)
            : ModelResult<StudentDomain, NetworkModelError>
}