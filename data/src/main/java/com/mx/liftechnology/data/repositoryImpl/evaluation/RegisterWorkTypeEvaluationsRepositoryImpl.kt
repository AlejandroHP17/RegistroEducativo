package com.mx.liftechnology.data.repositoryImpl.evaluation

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.core.network.api.RequestListGrades
import com.mx.liftechnology.core.network.api.RequestWorkTypeEvaluations
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toWorkTypeEvaluationDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.evaluation.WorkTypeEvaluationDomain
import com.mx.liftechnology.domain.repository.evaluation.RegisterWorkTypeEvaluationsRepository


class RegisterWorkTypeEvaluationsRepositoryImpl (
    private val evaluationApi: EvaluationApi
): RegisterWorkTypeEvaluationsRepository {
    override suspend fun register(
        formativeFieldId : Int,
        partialId : Int,
        workTypeId : Int,
        nameWork : String,
        workDate : String,
        schoolCycleId : Int,
        grades : List<RequestListGrades>
    ): ModelResult<WorkTypeEvaluationDomain, NetworkModelError> {
        val request = RequestWorkTypeEvaluations(
            formativeFieldId = formativeFieldId,
            partialId = partialId,
            workTypeId = workTypeId,
            nameWork = nameWork,
            workDate = workDate,
            schoolCycleId = schoolCycleId,
            grades = grades
        )

        return safeApiCall(
            apiCall = { evaluationApi.registerWorkTypeEvaluations(request) },
            mapper = { it.toWorkTypeEvaluationDomain() }
        )
    }
}