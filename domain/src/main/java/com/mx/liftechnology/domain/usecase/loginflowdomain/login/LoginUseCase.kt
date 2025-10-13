package com.mx.liftechnology.domain.usecase.loginflowdomain.login

import android.os.Build
import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestLogin
import com.mx.liftechnology.core.network.apiCall.flowLogin.ResponseLogin
import com.mx.liftechnology.core.network.apiCall.flowLogin.UserLogin
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.LocationHelper
import com.mx.liftechnology.data.repository.flowLogin.login.LoginRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

/**
 * Use case for handling user login.
 *
 * @property repositoryLogin The repository for login operations.
 * @property locationHelper Helper to get the current device location.
 * @property preference Use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginUseCase(
    private val repositoryLogin: LoginRepository,
    private val locationHelper: LocationHelper,
    private val preference: PreferenceUseCase
) {

    /**
     * Executes the login process.
     *
     * @param email The user's email.
     * @param pass The user's password.
     * @param remember Whether to save the user's session.
     * @return A [ModelState] representing the result of the login attempt.
     */
    suspend operator fun invoke (email: String?, pass: String?, remember: Boolean): ModelState<UserLogin?, String> {
        val location = locationHelper.getCurrentLocation()
        val latitude = location?.latitude
        val longitude = location?.longitude

        val request = RequestLogin(
            email = email?.lowercase().orEmpty(),
            password = pass.orEmpty(),
            latitude = latitude?.toString().orEmpty(),
            longitude = longitude?.toString().orEmpty(),
            imei = Build.FINGERPRINT + Build.ID
        )

        return runCatching { repositoryLogin.executeLogin(request) }.fold(
            onSuccess = { result ->
                when (result){
                    is ResultSuccess -> {
                        result.data?.accessToken?.let {
                            if(savePreferences(result.data, remember)) SuccessState(result.data?.userLogin)
                            else ErrorState(ModelCodeError.ERROR_CRITICAL)
                        }?:ErrorState(ModelCodeError.ERROR_CRITICAL)
                    }
                    is ResultError -> {
                        handleResponse(result.error)
                    }

                }
            },
            onFailure = { ErrorState(ModelCodeError.ERROR_CRITICAL) }
        )
    }

    /**
     * Saves user preferences after a successful login.
     *
     * @param result The login response data.
     * @param remember Whether to save the login session.
     * @return True if the preferences were saved successfully, false otherwise.
     */
    private fun savePreferences(result: ResponseLogin?, remember:Boolean): Boolean {
        return result?.userLogin?.let { data ->
            preference.savePreferenceString(ModelPreference.ACCESS_TOKEN, result.accessToken)
            preference.savePreferenceInt(ModelPreference.ID_USER, data.userId)
            preference.savePreferenceInt(ModelPreference.ID_ROLE,
                if (data.teacherId == null) data.studentId
                else data.teacherId
            )
            preference.savePreferenceString(ModelPreference.USER_ROLE, data.role)
            preference.savePreferenceBoolean(ModelPreference.LOGIN, remember)
            true
        }?: false
    }

    /**
     * Handles error responses from the login repository.
     *
     * @param error The [FailureService] object representing the error.
     * @return A [ModelState] representing the specific error.
     */
    private fun handleResponse(error: FailureService): ModelState<UserLogin?, String> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_LOGIN)
            is FailureService.Unauthorized -> ErrorState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_LOGIN)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

}
