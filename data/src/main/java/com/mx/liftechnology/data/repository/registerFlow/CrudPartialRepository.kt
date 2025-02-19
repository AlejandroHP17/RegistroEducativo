package com.mx.liftechnology.data.repository.registerFlow

import com.mx.liftechnology.core.network.callapi.CredentialsGetPartial
import com.mx.liftechnology.core.network.callapi.CredentialsRegisterPartial
import com.mx.liftechnology.core.network.callapi.GetPartialApiCall
import com.mx.liftechnology.core.network.callapi.RegisterPartialApiCall
import com.mx.liftechnology.core.network.callapi.ResponseGetPartial
import com.mx.liftechnology.core.network.util.ExceptionHandler
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultService
import com.mx.liftechnology.core.network.util.ResultSuccess
import retrofit2.HttpException

interface CrudPartialRepository{
  suspend fun executeRegisterPartial(
      request : CredentialsRegisterPartial
  ): ResultService<List<String?>?, FailureService>
  suspend fun executeGetPartial(
      request : CredentialsGetPartial
  ): ResultService<List<ResponseGetPartial?>?, FailureService>
}

class CrudPartialRepositoryImp(
    private val registerPartialApiCall: RegisterPartialApiCall,
    private val getPartialApiCall: GetPartialApiCall
) : CrudPartialRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun executeRegisterPartial(
        request : CredentialsRegisterPartial
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerPartialApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }

    override suspend fun executeGetPartial(
        request : CredentialsGetPartial
    ): ResultService<List<ResponseGetPartial?>?, FailureService> {
        return try {
            val response = getPartialApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}