/**
 * @file Define el repositorio para la funcionalidad de registro de una lista de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterListAssignmentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterAssignment
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
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
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeRegisterListAssignment(request : RequestRegisterAssignment): ModelResult<List<String>?, NetworkError>
}

/**
 * Implementación de [RegisterListAssignmentRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerListAssignmentApiCall La llamada a la API para el registro de una lista de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListAssignmentRepositoryImpl(
    private val registerListAssignmentApiCall: RegisterListAssignmentApiCall
): RegisterListAssignmentRepository {
    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterListAssignment(request: RequestRegisterAssignment) : ModelResult<List<String>?, NetworkError>{
        return try{
            val response = registerListAssignmentApiCall.callApi(request)
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