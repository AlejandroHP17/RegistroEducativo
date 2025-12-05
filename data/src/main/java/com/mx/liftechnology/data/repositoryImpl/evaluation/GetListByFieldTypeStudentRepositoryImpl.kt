package com.mx.liftechnology.data.repositoryImpl.evaluation

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toByFieldTypeStudentDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.formativeFields.ByFieldTypeStudentDomain
import com.mx.liftechnology.domain.repository.formativeFields.GetListByFieldTypeStudentRepository

class GetListByFieldTypeStudentRepositoryImpl(
    private val evaluationApi: EvaluationApi
): GetListByFieldTypeStudentRepository {
    override suspend fun getByFieldType(
        formativeFieldId : Int,
        workTypeId : Int,
        workName : String?,
        workDate : String?
    ): ModelResult<ByFieldTypeStudentDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = {
                evaluationApi.getListByFieldTypeStudent(
                    formativeFieldId = formativeFieldId,
                    workTypeId = workTypeId,
                    workName = workName,
                    workDate = workDate
                )
            },
            mapper = { it.toByFieldTypeStudentDomain() }
        )
    }
}