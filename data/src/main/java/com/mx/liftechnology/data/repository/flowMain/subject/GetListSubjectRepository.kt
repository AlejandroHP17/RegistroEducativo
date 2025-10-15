/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListSubjectApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListSubject
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListSubject
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interfaz del repositorio para la obtención de la lista de materias.
 * Define el contrato para ejecutar la lógica de obtención de la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListSubjectRepository{
    /**
     * Ejecuta la petición para obtener la lista de materias.
     *
     * @param request Los datos de la petición.
     * @return Un [ResultService] que indica el resultado de la operación.
     */
    suspend fun executeGetListSubject(request: RequestGetListSubject)
    : ResultService<List<ResponseGetListSubject?>?, FailureService>
}

/**
 * Implementación de [GetListSubjectRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListSubjectApiCall La llamada a la API para obtener la lista de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListSubjectRepositoryImp(
    private val getListSubjectApiCall : GetListSubjectApiCall
) : GetListSubjectRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListSubject(
        request: RequestGetListSubject
    ) : ResultService<List<ResponseGetListSubject?>?, FailureService> {
        return try {
            val response = getListSubjectApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}