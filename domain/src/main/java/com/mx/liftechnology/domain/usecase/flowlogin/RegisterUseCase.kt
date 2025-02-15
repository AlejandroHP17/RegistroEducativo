package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorUnauthorizedState
import com.mx.liftechnology.core.model.modelBase.ErrorUserState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.network.callapi.CredentialsRegister
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultSuccess
import com.mx.liftechnology.data.repository.loginFlow.RegisterRepository

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
    ):  ModelState<List<String>?, String> {
        val request = CredentialsRegister(
            email = email,
            password = pass,
            codigoactivacion = activatationCode
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