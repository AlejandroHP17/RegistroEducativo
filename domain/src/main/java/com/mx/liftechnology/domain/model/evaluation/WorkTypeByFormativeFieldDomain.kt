package com.mx.liftechnology.domain.model.evaluation

data class WorkTypeByFormativeFieldDomain(
    val formativeFieldName: String,
    val formativeFieldId: Int,
    val workTypes: List<WorkTypeDetailDomain>
)

data class WorkTypeDetailDomain(
    val workTypeName: String,
    val workTypeId: Int,
    val evaluationWeight: String
)