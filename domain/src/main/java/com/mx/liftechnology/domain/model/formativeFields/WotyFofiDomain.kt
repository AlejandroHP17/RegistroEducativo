package com.mx.liftechnology.domain.model.formativeFields

data class WotyFofiDomain(
    val formativeFields: List<ListFormativeFieldsDomain>,
)

data class ListFormativeFieldsDomain(
    val formativeFieldId: Int,
    val formativeFieldName: String,
    val code: String,
    val listWorkTypes: List<ListWorkTypesDomain>
)

data class ListWorkTypesDomain(
    val workTypeId: Int,
    val workTypeName: String,
    val evaluationWeight: String
)