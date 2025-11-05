/**
 * @file Define el repositorio para la funcionalidad de registro de escuelas.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.school

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSchoolApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterSchool
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de escuelas.
 * Define el contrato para ejecutar la lógica de registro de escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterSchoolRepository{
  /**
   * Ejecuta la petición de registro de una escuela.
   *
   * @param request Los datos de la petición de registro.
   * @return Un [ModelResult] que indica el resultado de la operación.
   */
  suspend fun executeRegisterOneSchool(
      request : RequestRegisterSchool
  ): ModelResult<List<String?>?, NetworkError>
}

/**
 * Implementación de [RegisterSchoolRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerSchoolApiCall La llamada a la API para el registro de escuelas.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterSchoolRepositoryImpl(
    private val registerSchoolApiCall: RegisterSchoolApiCall
) : RegisterSchoolRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterOneSchool(
        request : RequestRegisterSchool
    ): ModelResult<List<String?>?, NetworkError> {
        return try {
            val response = registerSchoolApiCall.callApi(request)
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