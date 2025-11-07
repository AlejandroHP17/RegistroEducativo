/**
 * @file Define el repositorio para la funcionalidad de registro de una lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.partial

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterListPartialApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterPartial
import com.mx.liftechnology.data.mapper.DataToDomainMapper.mapperToModelListPartialsData
import com.mx.liftechnology.data.model.ModelListPartialsData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
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
   * @return Un [ModelResult] que indica el resultado de la operación.
   */
  suspend fun executeRegisterListPartial(request : RequestRegisterPartial
  ): ModelResult<List<ModelListPartialsData?>, NetworkError>
}

/**
 * Implementación de [RegisterListPartialRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerListPartialApiCall La llamada a la API para el registro de una lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListPartialRepositoryImpl(
    private val registerListPartialApiCall: RegisterListPartialApiCall,
) : RegisterListPartialRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterListPartial(
        request : RequestRegisterPartial
    ): ModelResult<List<ModelListPartialsData?>, NetworkError> {
        return try {
            val response = registerListPartialApiCall.callApi(request)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelListPartialsData())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}