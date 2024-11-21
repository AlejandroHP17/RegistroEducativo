package com.mx.liftechnology.data.repository.mainFlow

import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.model.ModelApi.GenericResponse
import com.mx.liftechnology.core.network.callapi.CCTApiCall


fun interface CCTRepository{
  suspend fun executeCCT(): GenericResponse<List<DataCCT?>?>?
}

class CCTRepositoryImp(
    private val CCTApiCall: CCTApiCall
) :  CCTRepository {

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
}