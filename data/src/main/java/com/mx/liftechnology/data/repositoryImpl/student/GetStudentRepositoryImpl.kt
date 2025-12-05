package com.mx.liftechnology.data.repositoryImpl.student

import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.StudentMapper.toListStudentDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.repository.student.GetStudentRepository

/**
 * Implementación de [GetStudentRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property StudentApi La llamada a la API para obtener la lista de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetStudentRepositoryImpl(
    private val studentApi: StudentApi
) : GetStudentRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getStudents(
        cycleSchoolId: Int
    ) : ModelResult<List<StudentDomain>, NetworkModelError> {
        return safeApiCall(
            apiCall = { studentApi.getListStudents(cycleSchoolId) },
            mapper = { it.toListStudentDomain() }
        )
    }
}