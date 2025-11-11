package com.mx.liftechnology.data.model.formativeField

data class ModelWotyFofiData(
    val formativeFields: List<ResponseFormativeFieldsData>,
)

data class ResponseFormativeFieldsData(
    val formativeFieldId: Int,
    val formativeFieldName: String,
    val code: String,
    val listWorkTypes: List<ResponseWorkTypesData>
)

data class ResponseWorkTypesData(
    val workTypeId: Int,
    val workTypeName: String,
    val evaluationWeight: String
)