package com.mx.liftechnology.domain.usecase.loginflowdomain.register

import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestRegisterUser
import com.mx.liftechnology.data.repository.flowLogin.register.RegisterUserRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

/**
 * Use case for handling user registration.
 *
 * @property registerUserRepository The repository for user registration operations.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserUseCase(
    private val registerUserRepository: RegisterUserRepository
)  {
    /**
     * Executes the user registration process.
     *
     * @param email The user's email.
     * @param pass The user's password.
     * @param activatationCode The activation code for the account.
     * @return A [ModelState] representing the result of the registration attempt.
     */
    suspend operator fun invoke(email: String, pass: String, activatationCode: String): ModelState<List<String>?, String> {
        val request = RequestRegisterUser(
            email = email.lowercase(),
            password = pass,
            activationCode = activatationCode
        )
        return when (val result =  registerUserRepository.executeRegisterUser(request)) {
            is ResultSuccess -> {
                SuccessState(result.data)
            }
            is ResultError -> {
                handleResponse(result.error)
            }
        }
    }

    /**
     * Handles error responses from the registration repository.
     *
     * @param error The [FailureService] object representing the error.
     * @return A [ModelState] representing the specific error.
     */
    private fun handleResponse(error: FailureService): ModelState<List<String>?, String> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_INFO)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}