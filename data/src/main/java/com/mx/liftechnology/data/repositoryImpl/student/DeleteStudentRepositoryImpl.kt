/**
 * @file Define el repositorio para la eliminación de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.student

import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError
import com.mx.liftechnology.domain.repository.student.DeleteStudentRepository

/**
 * Implementación de [DeleteStudentRepository].
 * Se encarga de realizar la llamada a la API para eliminar un estudiante y gestionar las respuestas.
 *
 * @property studentApi La API de estudiantes para realizar la operación de eliminación.
 * @author Pelkidev
 * @version 1.0.0
 */
class DeleteStudentRepositoryImpl(
    private val studentApi: StudentApi
) : DeleteStudentRepository {
    /**
     * {@inheritDoc}
     */
    override suspend fun delete(studentId: Int): ModelResult<String, NetworkModelError> {
        return studentApi.deleteStudent(studentId).executeOrError { it }
    }
}