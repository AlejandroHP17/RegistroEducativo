package com.mx.liftechnology.data.repository.mainFlow

import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.model.ModelApi.GenericResponse
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.network.callapi.CCTApiCall


fun interface CCTRepository{
  suspend fun executeCCT(): ModelState<List<DataCCT?>?, String>?
}

class CCTRepositoryImp(
    private val cctApiCall: CCTApiCall
) :  CCTRepository {

    override suspend fun executeCCT(): ModelState<List<DataCCT?>?, String>? {
        return try {
            val response = cctApiCall.callApi( )
            if (response.isSuccessful) {
                handleResponse(response.body())
            } else {
                ErrorState(ModelCodeError.ERROR_NETWORK)
            }

        } catch (e: Exception) {
            // Manejo de excepciones
            ErrorState(e.message?:ModelCodeError.ERROR_CATCH )
        }
    }

    /**
     * Maneja la respuesta del servidor y retorna el estado adecuado.
     */
    private fun handleResponse(responseBody: GenericResponse<List<DataCCT?>?>?): ModelState<List<DataCCT?>?, String>? {
        return when (responseBody?.response?.code) {
            200 -> SuccessState(responseBody.data)
            400 -> ErrorState(ModelCodeError.ERROR_INCOMPLETE_DATA)
            500 -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}