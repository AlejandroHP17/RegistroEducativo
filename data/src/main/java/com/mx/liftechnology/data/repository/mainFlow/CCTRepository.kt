package com.mx.liftechnology.data.repository.mainFlow

import com.mx.liftechnology.core.network.callapi.GetCctApiCall
import com.mx.liftechnology.core.network.callapi.ResponseCctSchool
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException


fun interface CCTRepository{
  suspend fun executeSchoolCCT(cct:String): ResultService<ResponseCctSchool?, FailureService>
}

class CCTRepositoryImp(
    private val cctApiCall: GetCctApiCall
) :  CCTRepository {

    override suspend fun executeSchoolCCT(cct:String): ResultService<ResponseCctSchool?, FailureService> {
        return try {
            val response = cctApiCall.callApi(cct)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}