package com.mx.liftechnology.data.repository.loginflowdata

import com.mx.liftechnology.core.network.callapi.CredentialsRegister
import com.mx.liftechnology.core.network.callapi.RegisterApiCall
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface RegisterRepository{
  suspend fun executeRegister(request: CredentialsRegister)
  : ResultService<List<String>?, FailureService>
}

class RegisterRepositoryImp(
    private val registerApiCall: RegisterApiCall
) :  RegisterRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun executeRegister(
        request: CredentialsRegister
    ): ResultService<List<String>?, FailureService> {
        return try {
            val response = registerApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}