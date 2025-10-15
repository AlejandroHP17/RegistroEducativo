/**
 * @file Define el repositorio para la funcionalidad de obtención de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject.assessment

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListAssessmentTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListAssessmentType
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
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
     * @return Un [ResultService] que indica el resultado de la operación.
     */
    suspend fun executeGetListAssessment(request : RequestGetListAssessmentType)
            : ResultService<List<ResponseGetListAssessmentType?>?, FailureService>
}

/**
 * Implementación de [GetAssessmentTypeRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListAssessmentTypeApiCall La llamada a la API para obtener la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetAssessmentTypeRepositoryImp(
    private val getListAssessmentTypeApiCall: GetListAssessmentTypeApiCall
) : GetAssessmentTypeRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListAssessment(
        request : RequestGetListAssessmentType
    ): ResultService<List<ResponseGetListAssessmentType?>?, FailureService> {
        return try {
            val response = getListAssessmentTypeApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }

}