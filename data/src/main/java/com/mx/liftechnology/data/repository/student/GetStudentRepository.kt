/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.student

import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.mapper.StudentMapper.toData
import com.mx.liftechnology.data.model.student.StudentData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para la obtención de la lista de estudiantes.
 * Define el contrato para ejecutar la lógica de obtención de la lista de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetStudentRepository{
    /**
     * Obtiene la lista de estudiantes.
     *
     * @param cycleSchoolId El ID del ciclo escolar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getStudents(cycleSchoolId: Int)
    : ModelResult<List<StudentData?>, NetworkModelError>
}

/**
 * Implementación de [GetStudentRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListStudentApiCall La llamada a la API para obtener la lista de estudiantes.
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
    ) : ModelResult<List<StudentData?>, NetworkModelError> {
        return studentApi.getListStudents(cycleSchoolId).executeOrError { it.toData() }
    }
}