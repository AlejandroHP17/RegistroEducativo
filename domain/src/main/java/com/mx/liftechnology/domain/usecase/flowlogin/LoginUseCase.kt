package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.core.model.ModelApi.Data
import com.mx.liftechnology.core.model.ModelApi.User
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorStateUser
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.util.LocationHelper
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.data.repository.loginFlow.LoginRepository
import com.mx.liftechnology.core.preference.PreferenceUseCase

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

        return when (val result = repositoryLogin.execute(email?.lowercase(), pass, latitude, longitude)) {
            is SuccessState -> {
                result.result?.accessToken?.let {
                    if(savePreferences(result.result)) SuccessState(result.result?.user)
                    else ErrorStateUser(ModelCodeError.ERROR_CRITICAL)
                }?: ErrorStateUser(ModelCodeError.ERROR_CRITICAL)
            }
            is ErrorState -> ErrorState(result.result)
            is ErrorStateUser -> ErrorStateUser(result.result)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    private fun savePreferences(result: Data?): Boolean {
        return result?.user?.let { data ->
            preference.savePreferenceString(ModelPreference.ACCESS_TOKEN, result.accessToken)
            preference.savePreferenceInt(ModelPreference.ID_USER, data.userID)
            preference.savePreferenceInt (ModelPreference.ID_ROLE,
                if (data.teacherID == null) data.studentID
                else data.teacherID
            )
            preference.savePreferenceString(ModelPreference.USER_ROLE, data.role)
            true
        }?: false

    }
}
