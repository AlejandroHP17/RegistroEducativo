package com.mx.liftechnology.data.repository.student

import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para la obtención de CCT.
 * Define el contrato para ejecutar la lógica de obtención de CCT.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface DeleteStudentRepository{
    /**
     * Elimina un estudiante.
     *
     * @param studentId El ID del estudiante a eliminar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun delete(studentId: Int): ModelResult<String, NetworkModelError>
}


class DeleteStudentRepositoryImpl (
    private val studentApi: StudentApi
) : DeleteStudentRepository {
    override suspend fun delete(studentId: Int): ModelResult<String, NetworkModelError> {
        return studentApi.deleteStudent(studentId).executeOrError { it }
    }
}