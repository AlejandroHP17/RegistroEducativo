package com.mx.liftechnology.domain.repository.evaluation

import com.mx.liftechnology.core.network.api.RequestListGrades
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError

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