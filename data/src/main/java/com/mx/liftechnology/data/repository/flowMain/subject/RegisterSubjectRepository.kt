package com.mx.liftechnology.data.repository.flowMain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSubjectApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterSubject
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface RegisterSubjectRepository{
    suspend fun executeRegisterOneSubject(request : RequestRegisterSubject)
    : ResultService<List<String?>?, FailureService>
}

class RegisterSubjectRepositoryImp(
    private val registerSubjectApiCall: RegisterSubjectApiCall
) : RegisterSubjectRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun executeRegisterOneSubject(
        request : RequestRegisterSubject
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerSubjectApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}