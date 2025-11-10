/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.partial

import com.mx.liftechnology.core.network.apiCall.flowMain.partial.GetListPartialApiCall
import com.mx.liftechnology.data.mapper.DataToDomainMapper.mapperToModelListPartialsData
import com.mx.liftechnology.data.model.ModelListPartialsData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para la obtención de la lista de parciales.
 * Define el contrato para ejecutar la lógica de obtención de la lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListPartialRepository{
  /**
   * Ejecuta la petición para obtener la lista de parciales.
   *
   * @param request Los datos de la petición.
   * @return Un [ModelResult] que indica el resultado de la operación.
   */
  suspend fun executeGetListPartial(
      schoolCycleId : Int
  ): ModelResult<List<ModelListPartialsData?>, NetworkError>
}

/**
 * Implementación de [GetListPartialRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListPartialApiCall La llamada a la API para obtener la lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListPartialRepositoryImpl(
    private val getListPartialApiCall: GetListPartialApiCall
) : GetListPartialRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListPartial(
        schoolCycleId : Int
    ): ModelResult<List<ModelListPartialsData?>, NetworkError> {
        return try {
            val response = getListPartialApiCall.callApi(schoolCycleId)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelListPartialsData())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkError.UNKNOWN)
        }
    }
}