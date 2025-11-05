/**
 * @file Define el repositorio para la funcionalidad de obtención del porcentaje de una materia.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.GetPercentSubjectIdApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetPercentSubjectId
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPercentSubjectId
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para la obtención del porcentaje de una materia.
 * Define el contrato para ejecutar la lógica de obtención del porcentaje de una materia.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetPercentSubjectRepository{
    /**
     * Ejecuta la petición para obtener el porcentaje de una materia.
     *
     * @param request Los datos de la petición.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeGetPercentSubject(request : RequestGetPercentSubjectId): ModelResult<List<ResponseGetPercentSubjectId>?, NetworkError>
}

/**
 * Implementación de [GetPercentSubjectRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getPercentSubjectIdApiCall La llamada a la API para obtener el porcentaje de una materia.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetPercentSubjectRepositoryImpl(private val getPercentSubjectIdApiCall: GetPercentSubjectIdApiCall
): GetPercentSubjectRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetPercentSubject(request: RequestGetPercentSubjectId) : ModelResult<List<ResponseGetPercentSubjectId>?, NetworkError>{
        return try{
            val response = getPercentSubjectIdApiCall.callApi(request)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it)
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        }catch (e:Exception){
            ErrorResult(NetworkException.handleException(e))
        }
    }
}