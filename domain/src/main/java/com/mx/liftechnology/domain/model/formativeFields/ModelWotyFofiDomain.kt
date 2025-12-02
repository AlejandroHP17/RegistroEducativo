package com.mx.liftechnology.domain.model.formativeFields

data class ModelWotyFofiDomain(
    val formativeFields: List<ResponseFormativeFieldsDomain>,
)

data class ResponseFormativeFieldsDomain(
    val formativeFieldId: Int,
    val formativeFieldName: String,
    val code: String,
    val listWorkTypes: List<ResponseWorkTypesDomain>
)

data class ResponseWorkTypesDomain(
    val workTypeId: Int,
    val workTypeName: String,
    val evaluationWeight: String
)