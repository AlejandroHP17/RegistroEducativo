/**
 * @file Define el repositorio para la funcionalidad de obtención de CCT.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.school

import com.mx.liftechnology.core.network.apiCall.flowMain.GetCctApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseCctSchool
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.MessageError
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess

/**
 * Interfaz del repositorio para la obtención de CCT.
 * Define el contrato para ejecutar la lógica de obtención de CCT.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetCctRepository{
  /**
   * Ejecuta la petición para obtener los datos de una escuela a partir de su CCT.
   *
   * @param cct El CCT de la escuela.
   * @return Un [ResultService] que indica el resultado de la operación.
   */
  suspend fun executeGetCct(cct:String): ResultService<ResponseCctSchool?, FailureService>
}

/**
 * Implementación de [GetCctRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property cctApiCall La llamada a la API para obtener los datos de la escuela.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetCctRepositoryImp(
    private val cctApiCall: GetCctApiCall
) : GetCctRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetCct(cct:String): ResultService<ResponseCctSchool?, FailureService> {
        return try {
            val response = cctApiCall.callApi(cct)
            response.body()?.data?.let {
                ResultSuccess(it)
            } ?: run {
                val exception = NullPointerException(MessageError.UNEXPECTED_NULL_BODY_ERROR_MESSAGE)
                ResultError(ExceptionHandler.handleException(exception))
            }
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}