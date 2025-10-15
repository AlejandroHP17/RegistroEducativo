/**
 * @file Define el repositorio para la funcionalidad de registro de una lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.partial

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterListPartialApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterPartial
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de una lista de parciales.
 * Define el contrato para ejecutar la lógica de registro de una lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterListPartialRepository{
  /**
   * Ejecuta la petición de registro de una lista de parciales.
   *
   * @param request Los datos de la petición de registro.
   * @return Un [ResultService] que indica el resultado de la operación.
   */
  suspend fun executeRegisterListPartial(request : RequestRegisterPartial
  ): ResultService<List<String?>?, FailureService>
}

/**
 * Implementación de [RegisterListPartialRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerListPartialApiCall La llamada a la API para el registro de una lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListPartialRepositoryImp(
    private val registerListPartialApiCall: RegisterListPartialApiCall,
) : RegisterListPartialRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterListPartial(
        request : RequestRegisterPartial
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerListPartialApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}