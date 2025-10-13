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
 * Interface for the assignment registration repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterAssignmentRepository{
    /**
     * Executes the assignment registration request.
     *
     * @param request The assignment registration request data.
     * @return A [ResultService] indicating the result of the operation.
     */
    suspend fun RegisterAssignment (request : RequestRegisterJobStudent):ResultService<List<ResponseStudentJobs?>?, FailureService>
}

/**
 * Implementation of [RegisterAssignmentRepository].
 *
 * @property repositoryRegisterOneJobStudent The API call for registering an assignment.
 *
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