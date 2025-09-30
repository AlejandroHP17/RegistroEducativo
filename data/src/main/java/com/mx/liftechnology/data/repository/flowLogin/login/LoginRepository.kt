package com.mx.liftechnology.data.repository.flowLogin.login

import com.mx.liftechnology.core.network.apiCall.flowLogin.LoginApiCall
import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestLogin
import com.mx.liftechnology.core.network.apiCall.flowLogin.ResponseLogin
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface LoginRepository{
  suspend fun executeLogin(request: RequestLogin): ResultService<ResponseLogin?, FailureService>
}

class LoginRepositoryImp(
    private val loginApiCall: LoginApiCall
) : LoginRepository {

    override suspend fun executeLogin(
        request: RequestLogin
    ): ResultService<ResponseLogin?, FailureService> {
        return try {
            val response = loginApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}