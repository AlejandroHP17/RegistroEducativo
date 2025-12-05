package com.mx.liftechnology.domain.repository.evaluation

import com.mx.liftechnology.core.network.api.RequestListGrades
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.evaluation.WorkTypeEvaluationDomain

fun interface RegisterWorkTypeEvaluationsRepository{
    suspend fun register(
        formativeFieldId : Int,
        partialId : Int,
        workTypeId : Int,
        nameWork : String,
        workDate : String,
        schoolCycleId : Int,
        grades : List<RequestListGrades>
    ) : ModelResult<WorkTypeEvaluationDomain, NetworkModelError>
}