package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.core.model.ModelApi.Data
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.ModelApi.GenericResponse
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelRegex
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.util.LocationHelper
import com.mx.liftechnology.data.model.ModelPreference
import com.mx.liftechnology.data.repository.loginFlow.LoginRepository
import com.mx.liftechnology.domain.usecase.PreferenceUseCase

class LoginUseCase(
    private val repositoryLogin: LoginRepository,
    private val locationHelper: LocationHelper,
    private val preference: PreferenceUseCase
) {

    /** Request to Login
     * @author pelkidev
     * @since 1.0.0
     * */
    suspend fun login(email: String?, pass: String?): ModelState<GenericResponse<Data>?> {
        return runCatching {

            val location = locationHelper.getCurrentLocation()
            val latitude = location?.latitude
            val longitude = location?.longitude
            repositoryLogin.execute(email, pass, latitude, longitude)
        }
            .fold(
                onSuccess = { data ->
                    if (data != null) {
                        preference.savePreferenceString(ModelPreference.ACCESS_TOKEN,data.data?.access_token)
                        SuccessState(data)
                    }
                    else ErrorState(ModelCodeError.ERROR_FUNCTION)
                },
                onFailure = { exception ->
                    ErrorState(ModelCodeError.ERROR_SERVICE)
                }
            )
    }


    /** validateEmail
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateEmail(email: String?): ModelState<Int> {
        val patEmail = ModelRegex.EMAIL
        return when {
            email.isNullOrEmpty() -> {
                ErrorState(ModelCodeError.ET_EMPTY)
            }

            !patEmail.matches(email) -> {
                ErrorState(ModelCodeError.ET_FORMAT)
            }

            else -> {
                SuccessState(ModelCodeSuccess.ET_FORMAT)
            }
        }
    }

    /** validatePass
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validatePass(pass: String?): ModelState<Int> {
        return when {
            pass.isNullOrEmpty() -> {
                ErrorState(ModelCodeError.ET_EMPTY)
            }

            else -> {
                SuccessState(ModelCodeSuccess.ET_FORMAT)
            }
        }
    }
}