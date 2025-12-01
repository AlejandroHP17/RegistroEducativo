package com.mx.liftechnology.data.repository.evaluation

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.core.network.api.RequestListGrades
import com.mx.liftechnology.core.network.api.RequestWorkTypeEvaluations
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

fun interface RegisterWorkTypeEvaluationsRepository{
    suspend fun executeRegisterWorkTyperEvaluations(
            formativeFieldId : Int,
            partialId : Int,
            workTypeId : Int,
            nameWork : String,
            workDate : String,
            schoolCycleId : Int,
            grades : List<RequestListGrades>
    ) : ModelResult<Boolean, NetworkModelError>
}
class RegisterWorkTypeEvaluationsRepositoryImpl (
    private val evaluationApi: EvaluationApi
): RegisterWorkTypeEvaluationsRepository{
    override suspend fun executeRegisterWorkTyperEvaluations(
        formativeFieldId : Int,
        partialId : Int,
        workTypeId : Int,
        nameWork : String,
        workDate : String,
        schoolCycleId : Int,
        grades : List<RequestListGrades>
    ): ModelResult<Boolean, NetworkModelError> {

        val request = RequestWorkTypeEvaluations(
            formativeFieldId = formativeFieldId,
            partialId = partialId,
            workTypeId = workTypeId,
            nameWork = nameWork,
            workDate = workDate,
            schoolCycleId = schoolCycleId,
            grades = grades
        )

        return try {
            val response = evaluationApi.registerWorkTypeEvaluations(request)
            if(response.isSuccessful && response.body() != null){
                SuccessResult(true)
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        }catch (e: Exception){
            ErrorResult(NetworkException.handleException(e))
        }
    }
}