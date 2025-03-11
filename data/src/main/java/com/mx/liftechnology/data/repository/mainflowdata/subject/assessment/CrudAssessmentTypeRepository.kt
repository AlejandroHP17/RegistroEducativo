package com.mx.liftechnology.data.repository.mainflowdata.subject.assessment

import com.mx.liftechnology.core.network.callapi.CredentialsGetListAssessmentType
import com.mx.liftechnology.core.network.callapi.GetListAssessmentTypeApiCall
import com.mx.liftechnology.core.network.callapi.ResponseGetListAssessmentType
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException


interface CrudAssessmentTypeRepository{
    suspend fun executeGetListAssessment(request : CredentialsGetListAssessmentType)
            : ResultService<List<ResponseGetListAssessmentType?>?, FailureService>
}

class CrudAssessmentTypeRepositoryImp(
    private val getListAssessmentTypeApiCall: GetListAssessmentTypeApiCall
) : CrudAssessmentTypeRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun executeGetListAssessment(
        request : CredentialsGetListAssessmentType
    ): ResultService<List<ResponseGetListAssessmentType?>?, FailureService> {
        return try {
            val response = getListAssessmentTypeApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }

}