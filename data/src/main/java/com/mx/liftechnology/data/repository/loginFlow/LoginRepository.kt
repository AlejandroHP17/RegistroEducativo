package com.mx.liftechnology.data.repository.loginFlow

import com.mx.liftechnology.core.network.callapi.Credentials
import com.mx.liftechnology.core.network.callapi.LoginApiCall
import com.mx.liftechnology.core.network.callapi.ResponseDataLogin
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface LoginRepository{
  suspend fun executeLogin(request: Credentials): ResultService<ResponseDataLogin?, FailureService>
}

class LoginRepositoryImp(
    private val loginApiCall: LoginApiCall
) : LoginRepository {

    override suspend fun executeLogin(
        request: Credentials
    ): ResultService<ResponseDataLogin?, FailureService> {
        return try {
            val response = loginApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}