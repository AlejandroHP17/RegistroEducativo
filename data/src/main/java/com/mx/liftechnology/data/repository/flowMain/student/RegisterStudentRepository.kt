/**
 * @file Define el repositorio para la funcionalidad de registro de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.student

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterStudentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterStudent
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de estudiantes.
 * Define el contrato para ejecutar la lógica de registro de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterStudentRepository{
    /**
     * Ejecuta la petición de registro de un estudiante.
     *
     * @param request Los datos de la petición de registro.
     * @return Un [ResultService] que indica el resultado de la operación.
     */
    suspend fun executeRegisterOneStudent(request: RequestRegisterStudent)
    : ResultService<List<String?>?, FailureService>
}

/**
 * Implementación de [RegisterStudentRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerStudentApiCall La llamada a la API para el registro de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterStudentRepositoryImp(
    private val registerStudentApiCall: RegisterStudentApiCall,
) : RegisterStudentRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterOneStudent(
        request: RequestRegisterStudent
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerStudentApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}