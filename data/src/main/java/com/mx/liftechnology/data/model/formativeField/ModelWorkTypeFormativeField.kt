package com.mx.liftechnology.data.model.formativeField

data class ModelWorkTypeFormativeField(
    val formativeFieldId: Int,
    val nameFormativeField: String,
    val listWorks: List<ModelListWorkFormativeField>,
)

data class ModelListWorkFormativeField(
    val workId: Int,
    val workName: String,
    val listWorks:  List<ModelListWorkStudentFormativeField>,
)

data class ModelListWorkStudentFormativeField(
    val workStudentId: Int,
    val workStudentName: String,
    val workStudentDate: String?,
)