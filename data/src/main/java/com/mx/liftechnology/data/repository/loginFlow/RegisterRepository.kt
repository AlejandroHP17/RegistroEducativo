package com.mx.liftechnology.data.repository.loginFlow

import com.mx.liftechnology.core.model.ModelApi.GenericResponse
import com.mx.liftechnology.core.network.callapi.CredentialsRegister
import com.mx.liftechnology.core.network.callapi.RegisterApiCall


fun interface RegisterRepository{
  suspend fun executeRegister(email: String,
                              pass: String,
                              activatationCode: String): GenericResponse<String>?
}

class RegisterRepositoryImp(
    private val registerApiCall: RegisterApiCall
) :  RegisterRepository {

    override suspend fun executeRegister(email: String,
                                         pass: String,
                                         activatationCode: String): GenericResponse<String>? {
        return try {
            val request = CredentialsRegister(email!!,pass!!, activatationCode!! )
            val response = registerApiCall.callApi(request)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Maneja el error en la respuesta
                null
            }
        } catch (e: Exception) {
            // Maneja la excepción
            null
        }
    }

}