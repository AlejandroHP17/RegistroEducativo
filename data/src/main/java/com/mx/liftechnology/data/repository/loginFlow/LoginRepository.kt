package com.mx.liftechnology.data.repository.loginFlow

import android.os.Build
import com.mx.liftechnology.core.model.modelApi.ResponseDataLogin
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.callapi.Credentials
import com.mx.liftechnology.core.network.callapi.LoginApiCall
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorStateUser
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import retrofit2.Response

fun interface LoginRepository{
  suspend fun execute(email: String?, pass: String?, latitude: Double?, longitude: Double?): ModelState<ResponseDataLogin?, String>?
}

class LoginRepositoryImp(
    private val loginApiCall: LoginApiCall
) : LoginRepository {

    /** Execute the login
     * @author pelkidev
     * @since 1.0.0
     * @param email the user
     * @param pass the user
     * @param latitude the dispositive
     * @param longitude the dispositive
     */
    override suspend fun execute(
        email: String?,
        pass: String?,
        latitude: Double?,
        longitude: Double?
    ): ModelState<ResponseDataLogin?, String> {
        return try {
            val request = Credentials(
                email = email.orEmpty(),
                password = pass.orEmpty(),
                latitude = latitude?.toString().orEmpty(),
                longitude = longitude?.toString().orEmpty(),
                imei = Build.SERIAL + Build.FINGERPRINT + Build.ID
            )
            val response = loginApiCall.callApi(request)
            handleResponse(response)
        } catch (e: Exception) {
            // Manejo de excepciones
            ErrorState(ModelCodeError.ERROR_CATCH )
        }
    }

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * @param responseBody in order to validate the code and if is success, return the body
     * if not return the correct error
     * @return ModelState
     */
    private fun handleResponse(responseBody: Response<GenericResponse<ResponseDataLogin>?>): ModelState<ResponseDataLogin?, String> {
        return when (responseBody.code()) {
            200 -> SuccessState(responseBody.body()?.data)
            400 -> ErrorStateUser(ModelCodeError.ERROR_VALIDATION_LOGIN)
            404 -> ErrorStateUser(ModelCodeError.ERROR_VALIDATION_LOGIN)
            500 -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}
