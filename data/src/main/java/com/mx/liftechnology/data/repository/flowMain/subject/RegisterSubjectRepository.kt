package com.mx.liftechnology.data.repository.flowMain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSubjectApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterSubject
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the subject registration repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterSubjectRepository{
    /**
     * Executes the subject registration request.
     *
     * @param request The subject registration request data.
     * @return A [ResultService] indicating the result of the operation.
     */
    suspend fun executeRegisterOneSubject(request : RequestRegisterSubject)
    : ResultService<List<String?>?, FailureService>
}

/**
 * Implementation of [RegisterSubjectRepository].
 *
 * @property registerSubjectApiCall The API call for subject registration.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterSubjectRepositoryImp(
    private val registerSubjectApiCall: RegisterSubjectApiCall
) : RegisterSubjectRepository {

    /**
     * {@inheritDoc}
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