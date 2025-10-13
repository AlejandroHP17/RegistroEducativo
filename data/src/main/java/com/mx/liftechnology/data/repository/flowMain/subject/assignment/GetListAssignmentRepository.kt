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
 * Interface for the assignment list repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListAssignmentRepository{
    /**
     * Executes the request to get the list of assignments.
     *
     * @param request The request data.
     * @return A [ResultService] indicating the result of the operation.
     */
    suspend fun executeGetListAssignment(request : RequestGetListAssignment): ResultService<List<String>?, FailureService>
}

/**
 * Implementation of [GetListAssignmentRepository].
 *
 * @property getListAssignmentApiCall The API call for getting the assignment list.
 *
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