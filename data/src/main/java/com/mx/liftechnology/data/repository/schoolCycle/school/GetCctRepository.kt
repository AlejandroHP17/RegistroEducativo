/**
 * @file Define el repositorio para la funcionalidad de obtención de CCT.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.schoolCycle.school

import com.mx.liftechnology.core.network.apiCall.schoolCycle.GetCctApiCall
import com.mx.liftechnology.data.mapper.SchoolCycleDataToDomainMapper.mapperToRegisterSchool
import com.mx.liftechnology.data.model.schoolCycle.ModelCCTData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

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
   * @return Un [ModelResult] que indica el resultado de la operación.
   */
  suspend fun executeGetCct(cct:String): ModelResult<ModelCCTData, NetworkError>
}

/**
 * Implementación de [GetCctRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property cctApiCall La llamada a la API para obtener los datos de la escuela.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetCctRepositoryImpl(
    private val cctApiCall: GetCctApiCall
) : GetCctRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetCct(cct:String): ModelResult<ModelCCTData, NetworkError> {
        return try {
            val response = cctApiCall.callApi(cct)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToRegisterSchool())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}