package com.mx.liftechnology.data.repository.flowLogin

import com.mx.liftechnology.core.model.ModelApi.Data
import com.mx.liftechnology.core.network.callapi.Credentials
import com.mx.liftechnology.core.network.callapi.LoginCallApi
import com.mx.liftechnology.core.util.GenericResponse
import com.mx.liftechnology.core.util.ModelState


fun interface LoginRepository{
  suspend fun execute(email: String?, pass: String?): GenericResponse<Data>?
}


class LoginRepositoryImp(
  private val loginCallApi: LoginCallApi
) :  LoginRepository {



    override suspend fun execute(email: String?, pass: String?): GenericResponse<Data>? {
        return try {
            val request = Credentials(email!!,pass!!)
            val response = loginCallApi.callApi(request )
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