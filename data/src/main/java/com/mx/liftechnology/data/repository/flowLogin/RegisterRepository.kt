package com.mx.liftechnology.data.repository.flowLogin

import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.network.callapi.CCTApiCall
import com.mx.liftechnology.core.network.callapi.CredentialsRegister
import com.mx.liftechnology.core.network.callapi.RegisterApiCall
import com.mx.liftechnology.core.model.ModelApi.GenericResponse


interface RegisterRepository{
  suspend fun executeCCT(): GenericResponse<List<DataCCT?>?>?
  suspend fun executeRegister(email: String,
                              pass: String,
                              cct: String,
                              activatationCode: String): GenericResponse<String>?
}

class RegisterRepositoryImp(
    private val CCTApiCall: CCTApiCall,
    private val registerApiCall: RegisterApiCall,
) :  RegisterRepository {

    override suspend fun executeCCT(): GenericResponse<List<DataCCT?>?>? {
        return try {
            val response = CCTApiCall.callApi( )
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

    override suspend fun executeRegister(email: String,
                                         pass: String,
                                         cct: String,
                                         activatationCode: String): GenericResponse<String>? {
        return try {
            val request = CredentialsRegister(email!!,pass!!,cct!!,
                activatationCode!! )
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