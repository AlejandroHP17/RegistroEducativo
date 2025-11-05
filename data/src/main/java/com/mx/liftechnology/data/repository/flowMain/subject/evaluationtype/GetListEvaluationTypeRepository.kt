/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject.evaluationtype

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListEvaluationTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListEvaluationType
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para la obtención de la lista de tipos de evaluación.
 * Define el contrato para ejecutar la lógica de obtención de la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListEvaluationTypeRepository {
    /**
     * Ejecuta la petición para obtener la lista de tipos de evaluación.
     *
     * @param request Los datos de la petición.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeGetListEvaluationType( request: RequestGetListEvaluationType) : ModelResult<List<String>?, NetworkError>
}

/**
 * Implementación de [GetListEvaluationTypeRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListEvaluationTypeApiCall La llamada a la API para obtener la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListEvaluationTypeRepositoryImpl (
    private var getListEvaluationTypeApiCall : GetListEvaluationTypeApiCall
): GetListEvaluationTypeRepository{
    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListEvaluationType(request: RequestGetListEvaluationType): ModelResult<List<String>?, NetworkError> {
        return try {
            val response = getListEvaluationTypeApiCall.callApi(request)
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