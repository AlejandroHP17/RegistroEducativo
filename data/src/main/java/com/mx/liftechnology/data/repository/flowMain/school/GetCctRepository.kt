package com.mx.liftechnology.data.repository.flowMain.school

import com.mx.liftechnology.core.network.apiCall.flowMain.GetCctApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseCctSchool
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException


fun interface GetCctRepository{
  suspend fun executeGetCct(cct:String): ResultService<ResponseCctSchool?, FailureService>
}

class GetCctRepositoryImp(
    private val cctApiCall: GetCctApiCall
) : GetCctRepository {

    override suspend fun executeGetCct(cct:String): ResultService<ResponseCctSchool?, FailureService> {
        return try {
            val response = cctApiCall.callApi(cct)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}