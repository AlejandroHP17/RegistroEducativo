package com.mx.liftechnology.data.repository.flowLogin.register

import com.mx.liftechnology.core.network.apiCall.flowLogin.RegisterUserApiCall
import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestRegisterUser
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the user registration repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterUserRepository{
  /**
   * Executes the user registration request.
   *
   * @param request The user registration request data.
   * @return A [ResultService] indicating the result of the operation.
   */
  suspend fun executeRegisterUser(request: RequestRegisterUser)
  : ResultService<List<String>?, FailureService>
}

/**
 * Implementation of [RegisterUserRepository].
 *
 * @property registerUserApiCall The API call for user registration.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserRepositoryImp(
    private val registerUserApiCall: RegisterUserApiCall
) : RegisterUserRepository {

    /**
     * {@inheritDoc}
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