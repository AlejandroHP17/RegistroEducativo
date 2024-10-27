package com.mx.liftechnology.data.repository.flowLogin

import com.mx.liftechnology.core.model.ModelApi.Data
import com.mx.liftechnology.core.network.callapi.Credentials
import com.mx.liftechnology.core.network.callapi.LoginApiCall
import com.mx.liftechnology.core.util.GenericResponse


fun interface LoginRepository{
  suspend fun execute(email: String?, pass: String?): GenericResponse<Data>?
}


class LoginRepositoryImp(
  private val loginApiCall: LoginApiCall
) :  LoginRepository {



    override suspend fun execute(email: String?, pass: String?): GenericResponse<Data>? {
        return try {
            val request = Credentials(email!!,pass!!,"15.1112225",
            "15.11111", "1111111111" )
            val response = loginApiCall.callApi(request )
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