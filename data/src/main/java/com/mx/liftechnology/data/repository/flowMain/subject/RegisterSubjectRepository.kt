/**
 * @file Define el repositorio para la funcionalidad de registro de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSubjectApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterSubject
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de materias.
 * Define el contrato para ejecutar la lógica de registro de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterSubjectRepository{
    /**
     * Ejecuta la petición de registro de una materia.
     *
     * @param request Los datos de la petición de registro.
     * @return Un [ResultService] que indica el resultado de la operación.
     */
    suspend fun executeRegisterOneSubject(request : RequestRegisterSubject)
    : ResultService<List<String?>?, FailureService>
}

/**
 * Implementación de [RegisterSubjectRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerSubjectApiCall La llamada a la API para el registro de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterSubjectRepositoryImp(
    private val registerSubjectApiCall: RegisterSubjectApiCall
) : RegisterSubjectRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterOneSubject(
        request : RequestRegisterSubject
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerSubjectApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}