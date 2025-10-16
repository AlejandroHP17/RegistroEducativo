/**
 * @file Define el repositorio para la funcionalidad del menú principal.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.menu

import com.mx.liftechnology.core.network.apiCall.flowMain.GroupApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGroup
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGroupTeacher
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.MessageError
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el menú principal.
 * Define el contrato para obtener la lista de grupos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface MenuRepository{
    /**
     * Ejecuta la petición para obtener la lista de grupos.
     *
     * @param request Los datos de la petición.
     * @return Un [ResultService] que indica el resultado de la operación.
     */
    suspend fun executeGetGroup(
        request: RequestGroup
    ): ResultService<List<ResponseGroupTeacher>, FailureService>
}

/**
 * Implementación de [MenuRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property groupApiCall La llamada a la API para obtener la lista de grupos.
 * @author Pelkidev
 * @version 1.0.0
 */
class MenuRepositoryImp(
    private val groupApiCall: GroupApiCall
): MenuRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetGroup(request: RequestGroup): ResultService<List<ResponseGroupTeacher>, FailureService> {
        return try {
            val response = groupApiCall.callApi(request)
            if (response.isSuccessful || response.body()?.data != null) {
                response.body()?.data?.let { res ->
                    val data = res.filterNotNull()
                    if(data.isNotEmpty()) ResultSuccess(data)
                    else {
                        val exception = NullPointerException(MessageError.UNEXPECTED_NULL_BODY_ERROR_MESSAGE)
                        ResultError(ExceptionHandler.handleException(exception))
                    }
                } ?: run {
                    val exception = NullPointerException(MessageError.UNEXPECTED_NULL_BODY_ERROR_MESSAGE)
                    ResultError(ExceptionHandler.handleException(exception))
                }
            }
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}