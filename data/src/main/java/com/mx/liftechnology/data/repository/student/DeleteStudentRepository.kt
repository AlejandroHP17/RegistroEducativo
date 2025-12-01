/**
 * @file Define el repositorio para la eliminación de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.student

import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para la eliminación de estudiantes.
 * Define el contrato para ejecutar la lógica de eliminación de un estudiante del sistema.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface DeleteStudentRepository {
    /**
     * Elimina un estudiante del sistema.
     *
     * @param studentId El ID del estudiante a eliminar.
     * @return Un [ModelResult] que contiene un mensaje de confirmación en caso de éxito,
     *         o un [NetworkModelError] en caso de fallo.
     */
    suspend fun delete(studentId: Int): ModelResult<String, NetworkModelError>
}

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