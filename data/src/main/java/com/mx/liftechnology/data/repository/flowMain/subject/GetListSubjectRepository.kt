/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListSubjectApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListSubject
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListSubject
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
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
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeGetListSubject(request: RequestGetListSubject)
    : ModelResult<List<ResponseGetListSubject?>?, NetworkError>
}

/**
 * Implementación de [GetListSubjectRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListSubjectApiCall La llamada a la API para obtener la lista de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListSubjectRepositoryImpl(
    private val getListSubjectApiCall : GetListSubjectApiCall
) : GetListSubjectRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListSubject(
        request: RequestGetListSubject
    ) : ModelResult<List<ResponseGetListSubject?>?, NetworkError> {
        return try {
            val response = getListSubjectApiCall.callApi(request)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it)
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e:Exception){
            ErrorResult(NetworkException.handleException(e))
        }
    }
}