package com.mx.liftechnology.data.repository.flowMain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterListAssignmentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterAssignment
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface RegisterListAssignmentRepository{
    suspend fun executeRegisterListAssignment(request : RequestRegisterAssignment): ResultService<List<String>?, FailureService>
}

class RegisterListAssignmentRepositoryImp(
    private val registerListAssignmentApiCall: RegisterListAssignmentApiCall
): RegisterListAssignmentRepository {
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