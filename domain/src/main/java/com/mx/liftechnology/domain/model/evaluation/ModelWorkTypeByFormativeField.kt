package com.mx.liftechnology.domain.model.evaluation

data class ModelWorkTypeByFormativeField(
    val formativeFieldName: String,
    val formativeFieldId: Int,
    val workTypes: List<ModelWorkTypeDetail>
)

data class ModelWorkTypeDetail(
    val workTypeName: String,
    val workTypeId: Int,
    val evaluationWeight: String
)