package com.mx.liftechnology.data.repository.getFlow

import com.mx.liftechnology.core.network.callapi.CredentialGetListStudent
import com.mx.liftechnology.core.network.callapi.GetListStudentApiCall
import com.mx.liftechnology.core.network.callapi.ResponseGetStudent
import com.mx.liftechnology.core.network.util.ExceptionHandler
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultService
import com.mx.liftechnology.core.network.util.ResultSuccess
import retrofit2.HttpException

fun interface GetListStudentRepository {
    suspend fun executeGetListStudent(request: CredentialGetListStudent): ResultService<List<ResponseGetStudent?>?, FailureService>
}

class GetListStudentRepositoryImp(
    private val getListStudentApiCall : GetListStudentApiCall
) : GetListStudentRepository{

    override suspend fun executeGetListStudent(
        request: CredentialGetListStudent
    ) : ResultService<List<ResponseGetStudent?>?, FailureService> {
        return try {
            val response = getListStudentApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}