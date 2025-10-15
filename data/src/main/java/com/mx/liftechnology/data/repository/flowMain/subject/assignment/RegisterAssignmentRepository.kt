/**
 * @file Define el repositorio para la funcionalidad de registro de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowMain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterJobStudentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterJobStudent
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseStudentJobs
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
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
     * @return Un [ResultService] que indica el resultado de la operación.
     */
    suspend fun RegisterAssignment (request : RequestRegisterJobStudent):ResultService<List<ResponseStudentJobs?>?, FailureService>
}

/**
 * Implementación de [RegisterAssignmentRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property repositoryRegisterOneJobStudent La llamada a la API para el registro de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterAssignmentRepositoryImp (
    private val repositoryRegisterOneJobStudent: RegisterJobStudentApiCall
): RegisterAssignmentRepository {
    /**
     * {@inheritDoc}
     */
    override suspend fun RegisterAssignment(request: RequestRegisterJobStudent): ResultService<List<ResponseStudentJobs?>?, FailureService> {
        return try {
            val response = repositoryRegisterOneJobStudent.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}