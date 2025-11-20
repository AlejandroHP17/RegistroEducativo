package com.mx.liftechnology.data.repository.evaluation

import com.mx.liftechnology.core.network.apiCall.evaluation.RegisterWorkTypeEvaluationsApiCall
import com.mx.liftechnology.core.network.apiCall.evaluation.RequestWorkTypeEvaluations
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

fun interface RegisterWorkTypeEvaluationsRepository{
    suspend fun executeRegisterWorkTyperEvaluations(request:RequestWorkTypeEvaluations) : ModelResult<Boolean, NetworkModelError>
}
class RegisterWorkTypeEvaluationsRepositoryImpl (
    val registerWorkTypeEvaluationsApiCall: RegisterWorkTypeEvaluationsApiCall
): RegisterWorkTypeEvaluationsRepository{
    override suspend fun executeRegisterWorkTyperEvaluations(request: RequestWorkTypeEvaluations): ModelResult<Boolean, NetworkModelError> {
        return try {
            val response = registerWorkTypeEvaluationsApiCall.callApi(request)
            if(response.isSuccessful && response.body() != null){
                SuccessResult(true)
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        }catch (e: Exception){
            ErrorResult(NetworkException.handleException(e))
        }
    }
}