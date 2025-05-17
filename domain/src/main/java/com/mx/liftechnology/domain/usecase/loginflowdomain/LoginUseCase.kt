package com.mx.liftechnology.domain.usecase.loginflowdomain

import android.os.Build
import com.mx.liftechnology.core.network.callapi.Credentials
import com.mx.liftechnology.core.network.callapi.ResponseDataLogin
import com.mx.liftechnology.core.network.callapi.User
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.LocationHelper
import com.mx.liftechnology.data.repository.loginflowdata.LoginRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

class LoginUseCase(
    private val repositoryLogin: LoginRepository,
    private val locationHelper: LocationHelper,
    private val preference: PreferenceUseCase
) {

    /**
     * Login
     * @author pelkidev
     * @since 1.0.0
     */
    suspend operator fun invoke (email: String?, pass: String?, remember: Boolean): ModelState<User?, String> {
        val location = locationHelper.getCurrentLocation()
        val latitude = location?.latitude
        val longitude = location?.longitude

        val request = Credentials(
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
                            if(savePreferences(result.data, remember)) SuccessState(result.data?.user)
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


    private fun savePreferences(result: ResponseDataLogin?, remember:Boolean): Boolean {
        return result?.user?.let { data ->
            preference.savePreferenceString(ModelPreference.ACCESS_TOKEN, result.accessToken)
            preference.savePreferenceInt(ModelPreference.ID_USER, data.userID)
            preference.savePreferenceInt(ModelPreference.ID_ROLE,
                if (data.teacherID == null) data.studentID
                else data.teacherID
            )
            preference.savePreferenceString(ModelPreference.USER_ROLE, data.role)
            preference.savePreferenceBoolean(ModelPreference.LOGIN, remember)
            true
        }?: false
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
