/**
 * @file Define el repositorio para la funcionalidad de registro de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSubjectApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterSubject
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
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
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeRegisterOneSubject(request : RequestRegisterSubject)
    : ModelResult<List<String?>?, NetworkError>
}

/**
 * Implementación de [RegisterSubjectRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerSubjectApiCall La llamada a la API para el registro de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterSubjectRepositoryImpl(
    private val registerSubjectApiCall: RegisterSubjectApiCall
) : RegisterSubjectRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterOneSubject(
        request : RequestRegisterSubject
    ): ModelResult<List<String?>?, NetworkError> {
        return try {
            val response = registerSubjectApiCall.callApi(request)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it)
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}