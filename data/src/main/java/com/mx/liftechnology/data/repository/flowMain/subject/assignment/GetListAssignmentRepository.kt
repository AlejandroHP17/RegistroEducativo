package com.mx.liftechnology.data.repository.flowMain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListAssignmentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListAssignment
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface GetListAssignmentRepository{
    suspend fun executeGetListAssignment(request : RequestGetListAssignment): ResultService<List<String>?, FailureService>
}

class GetListAssignmentRepositoryImp(
    private val getListAssignmentApiCall: GetListAssignmentApiCall
): GetListAssignmentRepository {

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