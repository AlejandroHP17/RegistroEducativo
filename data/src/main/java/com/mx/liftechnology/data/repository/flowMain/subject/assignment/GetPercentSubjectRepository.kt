package com.mx.liftechnology.data.repository.flowMain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.GetPercentSubjectIdApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetPercentSubjectId
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPercentSubjectId
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface GetPercentSubjectRepository{
    suspend fun executeGetPercentSubject(request : RequestGetPercentSubjectId): ResultService<List<ResponseGetPercentSubjectId>?, FailureService>
}

class GetPercentSubjectRepositoryImp(private val getPercentSubjectIdApiCall: GetPercentSubjectIdApiCall
): GetPercentSubjectRepository {

    override suspend fun executeGetPercentSubject(request: RequestGetPercentSubjectId) : ResultService<List<ResponseGetPercentSubjectId>?, FailureService>{
        return try{
            val response = getPercentSubjectIdApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        }catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}