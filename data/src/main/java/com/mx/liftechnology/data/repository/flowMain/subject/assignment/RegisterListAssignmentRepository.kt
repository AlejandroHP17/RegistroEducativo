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
 * Interface for the assignment list registration repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterListAssignmentRepository{
    /**
     * Executes the request to register a list of assignments.
     *
     * @param request The request data.
     * @return A [ResultService] indicating the result of the operation.
     */
    suspend fun executeRegisterListAssignment(request : RequestRegisterAssignment): ResultService<List<String>?, FailureService>
}

/**
 * Implementation of [RegisterListAssignmentRepository].
 *
 * @property registerListAssignmentApiCall The API call for registering an assignment list.
 *
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