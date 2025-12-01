/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.student

import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.mapper.StudentMapper.mapperToModelListStudent
import com.mx.liftechnology.data.model.student.StudentData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para la obtención de la lista de estudiantes.
 * Define el contrato para ejecutar la lógica de obtención de la lista de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetStudentRepository{
    /**
     * Ejecuta la petición para obtener la lista de estudiantes.
     *
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeGetListStudent(cycleSchoolId: Int)
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
    override suspend fun executeGetListStudent(
        cycleSchoolId: Int
    ) : ModelResult<List<StudentData?>, NetworkModelError> {
        return try {
            val response = studentApi.getListStudents(cycleSchoolId)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelListStudent())
                } ?: ErrorResult(NetworkModelError.EMPTY)
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e:Exception){
            ErrorResult(NetworkException.handleException(e))
        }
    }
}