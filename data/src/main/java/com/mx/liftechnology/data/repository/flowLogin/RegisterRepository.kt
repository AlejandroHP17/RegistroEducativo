package com.mx.liftechnology.data.repository.flowLogin

import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.network.callapi.RegisterApiCall
import com.mx.liftechnology.core.util.GenericResponse


fun interface RegisterRepository{
  suspend fun executeCCT(): GenericResponse<DataCCT>?
}

class RegisterRepositoryImp(
  private val registerApiCall: RegisterApiCall
) :  RegisterRepository {

    override suspend fun executeCCT(): GenericResponse<DataCCT>? {
        return try {
            val response = registerApiCall.callApi( )
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