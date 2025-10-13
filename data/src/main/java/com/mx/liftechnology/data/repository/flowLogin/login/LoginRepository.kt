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

/**
 * Interface for the login repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface LoginRepository{
  /**
   * Executes the login request.
   *
   * @param request The login request data.
   * @return A [ResultService] indicating the result of the operation.
   */
  suspend fun executeLogin(request: RequestLogin): ResultService<ResponseLogin?, FailureService>
}

/**
 * Implementation of [LoginRepository].
 *
 * @property loginApiCall The API call for login.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginRepositoryImp(
    private val loginApiCall: LoginApiCall
) : LoginRepository {

    /**
     * {@inheritDoc}
     */
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