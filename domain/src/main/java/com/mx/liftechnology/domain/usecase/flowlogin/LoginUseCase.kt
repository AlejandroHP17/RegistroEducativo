package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.core.model.ModelApi.User
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorStateUser
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.util.LocationHelper
import com.mx.liftechnology.data.model.ModelPreference
import com.mx.liftechnology.data.repository.loginFlow.LoginRepository
import com.mx.liftechnology.domain.usecase.PreferenceUseCase

fun interface LoginUseCase {
    suspend fun login(email: String?, pass: String?): ModelState<User?, String>?
}

class LoginUseCaseImp(
    private val repositoryLogin: LoginRepository,
    private val locationHelper: LocationHelper,
    private val preference: PreferenceUseCase
) : LoginUseCase {

    /**
     * Login
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun login(email: String?, pass: String?): ModelState<User?, String> {
        val location = locationHelper.getCurrentLocation()
        val latitude = location?.latitude
        val longitude = location?.longitude

        return when (val result = repositoryLogin.execute(email, pass, latitude, longitude)) {
            is SuccessState -> {
                // Save the token
                result.result?.accessToken?.let { token ->
                    preference.savePreferenceString(ModelPreference.ACCESS_TOKEN, token)
                }
                SuccessState(result.result?.user)
            }
            is ErrorState -> ErrorState(result.result)
            is ErrorStateUser -> ErrorStateUser(result.result)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}
