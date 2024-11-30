package com.mx.liftechnology.data.repository.loginFlow

import com.mx.liftechnology.core.model.ModelApi.GenericResponse
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorStateUser
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.network.callapi.CredentialsRegister
import com.mx.liftechnology.core.network.callapi.RegisterApiCall
import retrofit2.Response


fun interface RegisterRepository{
  suspend fun executeRegister(email: String,
                              pass: String,
                              activationCode: String): ModelState<String?, String>
}

class RegisterRepositoryImp(
    private val registerApiCall: RegisterApiCall
) :  RegisterRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     * @param email the user
     * @param pass the user
     * @param activationCode the user
     */
    override suspend fun executeRegister(
        email: String,
        pass: String,
        activationCode: String
    ): ModelState<String?, String> {
        return try {
            val request = CredentialsRegister(
                email = email,
                password = pass,
                codigoactivacion = activationCode
            )
            val response = registerApiCall.callApi(request)
            handleResponse(response)
        } catch (e: Exception) {
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
    private fun handleResponse(responseBody: Response<GenericResponse<String>?>): ModelState<String?, String> {
        return when (responseBody.code()) {
            200 -> SuccessState(responseBody.body()?.data)
            400 -> ErrorState(ModelCodeError.ERROR_INCOMPLETE_DATA)
            401 -> ErrorStateUser(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            500 -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

}