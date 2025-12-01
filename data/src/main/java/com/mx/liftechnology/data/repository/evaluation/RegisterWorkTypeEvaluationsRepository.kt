package com.mx.liftechnology.data.repository.evaluation

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.core.network.api.RequestListGrades
import com.mx.liftechnology.core.network.api.RequestWorkTypeEvaluations
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.data.util.executeOrError

fun interface RegisterWorkTypeEvaluationsRepository{
    suspend fun register(
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
    override suspend fun register(
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

        return evaluationApi.registerWorkTypeEvaluations(request).executeOrError { true }
    }
}