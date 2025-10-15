/**
 * @file Define el repositorio para la funcionalidad de registro de una lista de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterListAssignmentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterAssignment
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de una lista de asignaciones.
 * Define el contrato para ejecutar la lógica de registro de una lista de asignaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterListAssignmentRepository{
    /**
     * Ejecuta la petición de registro de una lista de asignaciones.
     *
     * @param request Los datos de la petición de registro.
     * @return Un [ResultService] que indica el resultado de la operación.
     */
    suspend fun executeRegisterListAssignment(request : RequestRegisterAssignment): ResultService<List<String>?, FailureService>
}

/**
 * Implementación de [RegisterListAssignmentRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerListAssignmentApiCall La llamada a la API para el registro de una lista de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListAssignmentRepositoryImp(
    private val registerListAssignmentApiCall: RegisterListAssignmentApiCall
): RegisterListAssignmentRepository {
    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterListAssignment(request: RequestRegisterAssignment) : ResultService<List<String>?, FailureService>{
        return try{
            val response = registerListAssignmentApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        }catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}