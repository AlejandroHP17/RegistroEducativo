package com.mx.liftechnology.data.repository.mainflowdata.subject

import com.mx.liftechnology.core.network.callapi.CredentialGetListSubject
import com.mx.liftechnology.core.network.callapi.CredentialsRegisterSubject
import com.mx.liftechnology.core.network.callapi.GetListSubjectApiCall
import com.mx.liftechnology.core.network.callapi.RegisterOneSubjectApiCall
import com.mx.liftechnology.core.network.callapi.ResponseGetListSubject
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

interface CrudSubjectRepository{
    suspend fun executeRegisterOneSubject(request : CredentialsRegisterSubject)
    : ResultService<List<String?>?, FailureService>

    suspend fun executeGetListSubject(request: CredentialGetListSubject)
    : ResultService<List<ResponseGetListSubject?>?, FailureService>
}

class CrudSubjectRepositoryImp(
    private val registerOneSubjectApiCall: RegisterOneSubjectApiCall,
    private val getListSubjectApiCall : GetListSubjectApiCall
) : CrudSubjectRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun executeRegisterOneSubject(
        request : CredentialsRegisterSubject
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerOneSubjectApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }

    override suspend fun executeGetListSubject(
        request: CredentialGetListSubject
    ) : ResultService<List<ResponseGetListSubject?>?, FailureService> {
        return try {
            val response = getListSubjectApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}