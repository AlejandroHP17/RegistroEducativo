/**
 * @file Define el repositorio para la funcionalidad de obtención de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject.assessment

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListAssessmentTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListAssessmentType
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para la obtención de tipos de evaluación.
 * Define el contrato para ejecutar la lógica de obtención de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetAssessmentTypeRepository{
    /**
     * Ejecuta la petición para obtener la lista de tipos de evaluación.
     *
     * @param request Los datos de la petición.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeGetListAssessment(request : RequestGetListAssessmentType)
            : ModelResult<List<ResponseGetListAssessmentType?>?, NetworkError>
}

/**
 * Implementación de [GetAssessmentTypeRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListAssessmentTypeApiCall La llamada a la API para obtener la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetAssessmentTypeRepositoryImpl(
    private val getListAssessmentTypeApiCall: GetListAssessmentTypeApiCall
) : GetAssessmentTypeRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListAssessment(
        request : RequestGetListAssessmentType
    ): ModelResult<List<ResponseGetListAssessmentType?>?, NetworkError> {
        return try {
            val response = getListAssessmentTypeApiCall.callApi(request)
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