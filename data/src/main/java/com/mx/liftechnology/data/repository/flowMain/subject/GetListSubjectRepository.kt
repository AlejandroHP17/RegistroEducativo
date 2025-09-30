package com.mx.liftechnology.data.repository.flowMain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListSubjectApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListSubject
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListSubject
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface GetListSubjectRepository{
    suspend fun executeGetListSubject(request: RequestGetListSubject)
    : ResultService<List<ResponseGetListSubject?>?, FailureService>
}

class GetListSubjectRepositoryImp(
    private val getListSubjectApiCall : GetListSubjectApiCall
) : GetListSubjectRepository {

    override suspend fun executeGetListSubject(
        request: RequestGetListSubject
    ) : ResultService<List<ResponseGetListSubject?>?, FailureService> {
        return try {
            val response = getListSubjectApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}