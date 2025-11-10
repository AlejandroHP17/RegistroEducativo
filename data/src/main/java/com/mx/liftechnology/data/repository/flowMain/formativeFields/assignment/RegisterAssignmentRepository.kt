/**
 * @file Define el repositorio para la funcionalidad de registro de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.formativeFields.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterJobStudentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterJobStudent
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseStudentJobs
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de asignaciones.
 * Define el contrato para ejecutar la lógica de registro de asignaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterAssignmentRepository{
    /**
     * Ejecuta la petición de registro de una asignación.
     *
     * @param request Los datos de la petición de registro.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeRegisterAssignment (request : RequestRegisterJobStudent): ModelResult<List<ResponseStudentJobs?>?, NetworkError>
}

/**
 * Implementación de [RegisterAssignmentRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property repositoryRegisterOneJobStudent La llamada a la API para el registro de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterAssignmentRepositoryImpl (
    private val repositoryRegisterOneJobStudent: RegisterJobStudentApiCall
): RegisterAssignmentRepository {
    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterAssignment(request: RequestRegisterJobStudent): ModelResult<List<ResponseStudentJobs?>?, NetworkError> {
        return try {
            val response = repositoryRegisterOneJobStudent.callApi(request)
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