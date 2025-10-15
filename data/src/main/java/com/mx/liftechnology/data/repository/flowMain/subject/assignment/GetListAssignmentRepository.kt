/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListAssignmentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListAssignment
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interfaz del repositorio para la obtención de la lista de asignaciones.
 * Define el contrato para ejecutar la lógica de obtención de la lista de asignaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListAssignmentRepository{
    /**
     * Ejecuta la petición para obtener la lista de asignaciones.
     *
     * @param request Los datos de la petición.
     * @return Un [ResultService] que indica el resultado de la operación.
     */
    suspend fun executeGetListAssignment(request : RequestGetListAssignment): ResultService<List<String>?, FailureService>
}

/**
 * Implementación de [GetListAssignmentRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListAssignmentApiCall La llamada a la API para obtener la lista de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListAssignmentRepositoryImp(
    private val getListAssignmentApiCall: GetListAssignmentApiCall
): GetListAssignmentRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListAssignment(request: RequestGetListAssignment) : ResultService<List<String>?, FailureService>{
        return try{
            val response = getListAssignmentApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        }catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }

}