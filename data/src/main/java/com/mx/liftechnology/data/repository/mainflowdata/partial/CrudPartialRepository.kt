package com.mx.liftechnology.data.repository.mainflowdata.partial

import com.mx.liftechnology.core.network.callapi.CredentialsGetPartial
import com.mx.liftechnology.core.network.callapi.CredentialsRegisterPartial
import com.mx.liftechnology.core.network.callapi.GetListPartialApiCall
import com.mx.liftechnology.core.network.callapi.RegisterListPartialApiCall
import com.mx.liftechnology.core.network.callapi.ResponseGetPartial
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

interface CrudPartialRepository{
  suspend fun executeRegisterListPartial(request : CredentialsRegisterPartial
  ): ResultService<List<String?>?, FailureService>
  suspend fun executeGetListPartial(
      request : CredentialsGetPartial
  ): ResultService<List<ResponseGetPartial?>?, FailureService>
}

class CrudPartialRepositoryImp(
    private val registerListPartialApiCall: RegisterListPartialApiCall,
    private val getListPartialApiCall: GetListPartialApiCall
) : CrudPartialRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun executeRegisterListPartial(
        request : CredentialsRegisterPartial
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerListPartialApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }

    override suspend fun executeGetListPartial(
        request : CredentialsGetPartial
    ): ResultService<List<ResponseGetPartial?>?, FailureService> {
        return try {
            val response = getListPartialApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}