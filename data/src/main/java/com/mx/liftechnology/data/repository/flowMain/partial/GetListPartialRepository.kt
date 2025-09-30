package com.mx.liftechnology.data.repository.flowMain.partial

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListPartialApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetPartial
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPartial
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface GetListPartialRepository{
  suspend fun executeGetListPartial(
      request : RequestGetPartial
  ): ResultService<List<ResponseGetPartial?>?, FailureService>
}

class GetListPartialRepositoryImp(
    private val getListPartialApiCall: GetListPartialApiCall
) : GetListPartialRepository {

    override suspend fun executeGetListPartial(
        request : RequestGetPartial
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