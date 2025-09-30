package com.mx.liftechnology.data.repository.flowLogin.register

import com.mx.liftechnology.core.network.apiCall.flowLogin.RegisterUserApiCall
import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestRegisterUser
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface RegisterUserRepository{
  suspend fun executeRegisterUser(request: RequestRegisterUser)
  : ResultService<List<String>?, FailureService>
}

class RegisterUserRepositoryImp(
    private val registerUserApiCall: RegisterUserApiCall
) : RegisterUserRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun executeRegisterUser(
        request: RequestRegisterUser
    ): ResultService<List<String>?, FailureService> {
        return try {
            val response = registerUserApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}