package com.mx.liftechnology.domain.usecase.loginflowdomain

import com.mx.liftechnology.core.network.callapi.CredentialsRegister
import com.mx.liftechnology.data.repository.loginflowdata.RegisterRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

fun interface RegisterUseCase {
    suspend fun putRegister(email: String, pass: String, activatationCode: String): ModelState<List<String>?, String>?
}

class RegisterUseCaseImp(
    private val registerRepository: RegisterRepository
) : RegisterUseCase {

    /** Request to Register
     * @author pelkidev
     * @since 1.0.0
     * */
    override suspend fun putRegister(
        email: String, pass: String, activatationCode: String
    ): ModelState<List<String>?, String> {
        val request = CredentialsRegister(
            email = email.lowercase(),
            password = pass,
            activationCode = activatationCode
        )
        return when (val result =  registerRepository.executeRegister(request)) {
            is ResultSuccess -> {
                SuccessState(result.data)
            }
            is ResultError -> {
                handleResponse(result.error)
            }
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * @param error in order to validate the code and if is success, return the body
     * if not return the correct error
     * @return ModelState
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