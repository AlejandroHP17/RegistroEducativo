package com.mx.liftechnology.data.repository.mainFlow

import com.mx.liftechnology.core.model.modelApi.CctSchool
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.network.callapi.SchoolCCTApiCall
import retrofit2.Response


fun interface CCTRepository{
  suspend fun executeSchoolCCT(cct:String): ModelState<CctSchool?, String>
}

class CCTRepositoryImp(
    private val cctApiCall: SchoolCCTApiCall
) :  CCTRepository {

    override suspend fun executeSchoolCCT(cct:String): ModelState<CctSchool?, String>  {
        return try {
            val response = cctApiCall.callApi(cct)
            handleResponse(response)

        } catch (e: Exception) {
            // Manejo de excepciones
            ErrorState(e.message?:ModelCodeError.ERROR_CATCH )
        }
    }

    /**
     * Maneja la respuesta del servidor y retorna el estado adecuado.
     */
    private fun handleResponse(responseBody: Response<GenericResponse<CctSchool?>>): ModelState<CctSchool?, String> {
        return when (responseBody.code()) {
            200 -> SuccessState(responseBody.body()?.data)
            400 -> ErrorState(ModelCodeError.ERROR_INCOMPLETE_DATA)
            401 -> ErrorState(ModelCodeError.ERROR_INCOMPLETE_DATA)
            404 -> ErrorState(ModelCodeError.ERROR_INCOMPLETE_DATA)
            500 -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}