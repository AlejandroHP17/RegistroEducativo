package com.mx.liftechnology.data.repository.flowMain.subject.evaluationtype

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListEvaluationTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListEvaluationType
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface GetListEvaluationTypeRepository {
    suspend fun executeGetListEvaluationType( request: RequestGetListEvaluationType) : ResultService<List<String>?, FailureService>
}

class GetListEvaluationTypeRepositoryImp (
    private var getListEvaluationTypeApiCall : GetListEvaluationTypeApiCall
): GetListEvaluationTypeRepository{
    override suspend fun executeGetListEvaluationType(request: RequestGetListEvaluationType): ResultService<List<String>?, FailureService> {
        return try {
            val response = getListEvaluationTypeApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}