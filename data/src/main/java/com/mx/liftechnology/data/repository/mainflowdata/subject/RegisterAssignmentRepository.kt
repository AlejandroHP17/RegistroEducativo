package com.mx.liftechnology.data.repository.mainflowdata.subject

import com.mx.liftechnology.core.network.callapi.CredentialsRegisterOneJobStudent
import com.mx.liftechnology.core.network.callapi.RegisterOneJobStudentApiCall
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface RegisterAssignmentRepository{
    suspend fun executePutAssignment (request : CredentialsRegisterOneJobStudent):ResultService<List<String?>?, FailureService>
}
class RegisterAssignmentRepositoryImp (
    private val repositoryRegisterOneJobStudent: RegisterOneJobStudentApiCall
): RegisterAssignmentRepository {
    override suspend fun executePutAssignment(request: CredentialsRegisterOneJobStudent): ResultService<List<String?>?, FailureService> {
        return try {
            val response = repositoryRegisterOneJobStudent.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}