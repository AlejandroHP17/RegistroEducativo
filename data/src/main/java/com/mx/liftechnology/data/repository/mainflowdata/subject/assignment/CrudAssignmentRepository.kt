package com.mx.liftechnology.data.repository.mainflowdata.subject.assignment

import com.mx.liftechnology.core.network.callapi.CredentialsGetListAssignment
import com.mx.liftechnology.core.network.callapi.CredentialsGetPercentSubjectId
import com.mx.liftechnology.core.network.callapi.CredentialsRegisterAssignment
import com.mx.liftechnology.core.network.callapi.GetListAssignmentApiCall
import com.mx.liftechnology.core.network.callapi.GetPercentSubjectIdApiCall
import com.mx.liftechnology.core.network.callapi.RegisterListAssignmentApiCall
import com.mx.liftechnology.core.network.callapi.ResponseGetPercentSubjectId
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

interface CrudAssignmentRepository{
    suspend fun executeRegisterListAssignment(request : CredentialsRegisterAssignment): ResultService<List<String>?, FailureService>
    suspend fun executeGetListAssignment(request : CredentialsGetListAssignment): ResultService<List<String>?, FailureService>
    suspend fun executeGetPercentSubjectId(request : CredentialsGetPercentSubjectId): ResultService<List<ResponseGetPercentSubjectId>?, FailureService>
}

class CrudAssignmentRepositoryImp(
    private val registerListAssignmentApiCall: RegisterListAssignmentApiCall,
    private val getListAssignmentApiCall: GetListAssignmentApiCall,
    private val getPercentSubjectIdApiCall: GetPercentSubjectIdApiCall
): CrudAssignmentRepository {
    override suspend fun executeRegisterListAssignment(request: CredentialsRegisterAssignment) : ResultService<List<String>?, FailureService>{
        return try{
            val response = registerListAssignmentApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        }catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }

    override suspend fun executeGetListAssignment(request: CredentialsGetListAssignment) : ResultService<List<String>?, FailureService>{
        return try{
            val response = getListAssignmentApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        }catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }

    override suspend fun executeGetPercentSubjectId(request: CredentialsGetPercentSubjectId) : ResultService<List<ResponseGetPercentSubjectId>?, FailureService>{
        return try{
            val response = getPercentSubjectIdApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        }catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}