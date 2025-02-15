package com.mx.liftechnology.data.repository.getFlow

import com.mx.liftechnology.core.network.callapi.CredentialGetListSubject
import com.mx.liftechnology.core.network.callapi.GetListSubjectApiCall
import com.mx.liftechnology.core.network.util.ExceptionHandler
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultService
import com.mx.liftechnology.core.network.util.ResultSuccess
import retrofit2.HttpException

fun interface GetListSubjectRepository {
    suspend fun executeGetListSubject(request: CredentialGetListSubject): ResultService<List<String?>?, FailureService>
}

class GetListSubjectRepositoryImp(
    private val getListSubjectApiCall : GetListSubjectApiCall
) : GetListSubjectRepository{

    override suspend fun executeGetListSubject(
        request: CredentialGetListSubject
    ) : ResultService<List<String?>?, FailureService> {
        return try {
            val response = getListSubjectApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}