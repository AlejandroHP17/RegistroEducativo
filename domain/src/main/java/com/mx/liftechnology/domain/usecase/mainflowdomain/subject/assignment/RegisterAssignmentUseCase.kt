package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.callapi.User
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

class RegisterAssignmentUseCase {

    suspend operator fun invoke(nameJob: String, nameAssignment: String, date: String): ModelState<String, String> {
        /*val request = Credentials(
            nameJob = nameJob?.lowercase().orEmpty(),
            nameAssignment = nameAssignment.orEmpty(),
            date = date?.toString().orEmpty()
        )

        return runCatching { repositoryLogin.executeLogin(request) }.fold(
            onSuccess = { result ->
                when (result){
                    is ResultSuccess -> {
                        result.data?.accessToken?.let {
                            if(savePreferences(result.data, remember)) SuccessState(result.data?.user)
                            else ErrorState(ModelCodeError.ERROR_CRITICAL)
                        }?: ErrorState(ModelCodeError.ERROR_CRITICAL)
                    }
                    is ResultError -> {
                        handleResponse(result.error)
                    }

                }
            },
            onFailure = { ErrorState(ModelCodeError.ERROR_CRITICAL) }
        )*/
        return SuccessState("")
    }

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * @param error in order to validate the code and if is success, return the body
     * if not return the correct error
     * @return ModelState
     */
    private fun handleResponse(error: FailureService): ModelState<User?, String> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_LOGIN)
            is FailureService.Unauthorized -> ErrorState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_LOGIN)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}