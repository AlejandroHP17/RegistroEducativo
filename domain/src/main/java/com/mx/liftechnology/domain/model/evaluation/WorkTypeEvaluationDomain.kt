package com.mx.liftechnology.domain.model.evaluation

import com.mx.liftechnology.core.network.api.ResponseCreatedWorks

data class WorkTypeEvaluationDomain(
    val createdWorks: List<ResponseCreatedWorks>,
    val totalStudentsWithGrade: Int,
    val totalStudentsWithoutGrade: Int,
    val formativeFieldName: String?,
    val partialName: String?,
    val workTypeId: Int,
    val workTypeName: String,
    val nameWork: String,
)
