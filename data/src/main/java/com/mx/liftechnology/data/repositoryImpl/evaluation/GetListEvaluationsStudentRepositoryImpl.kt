package com.mx.liftechnology.data.repositoryImpl.evaluation

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toEvaluationsStudentDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.student.EvaluationsStudentDomain
import com.mx.liftechnology.domain.repository.student.GetListEvaluationsStudentRepository

class GetListEvaluationsStudentRepositoryImpl(
    private val evaluationApi: EvaluationApi
): GetListEvaluationsStudentRepository {
    override suspend fun getListEvaluations(
        schoolCycleId: Int,
        partialId: Int,
        formativeFieldId: Int,
        workTypeId: Int,
        studentId: Int,
        workDate: String?,
        workDateFrom : String?,
        workDateTo: String?
    ): ModelResult<List<EvaluationsStudentDomain>, NetworkModelError> {
        return safeApiCall(
            apiCall = {
                evaluationApi.getListEvaluations(
                    formativeFieldId = formativeFieldId,
                    partialId = partialId,
                    workTypeId = workTypeId,
                    schoolCycleId = schoolCycleId,
                    studentId = studentId,
                    workDate = workDate,
                    workDateFrom = workDateFrom,
                    workDateTo = workDateTo,
                )
            },
            mapper = { it.toEvaluationsStudentDomain() }
        )
    }
}