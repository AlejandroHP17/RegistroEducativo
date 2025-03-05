package com.mx.liftechnology.data.repository.mainflowdata.subject.evaluationtype

import com.mx.liftechnology.core.network.callapi.CredentialsGetListEvaluationType
import com.mx.liftechnology.core.network.callapi.GetListEvaluationTypeApiCall
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

interface CrudEvaluationTypeRepository {
    suspend fun executeGetListEvaluationType( request: CredentialsGetListEvaluationType) : ResultService<List<String>?, FailureService>
}

class CrudEvaluationTypeRepositoryImp (
    private var getListEvaluationTypeApiCall : GetListEvaluationTypeApiCall
): CrudEvaluationTypeRepository{
    override suspend fun executeGetListEvaluationType(request: CredentialsGetListEvaluationType): ResultService<List<String>?, FailureService> {
        return try {
            val response = getListEvaluationTypeApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}