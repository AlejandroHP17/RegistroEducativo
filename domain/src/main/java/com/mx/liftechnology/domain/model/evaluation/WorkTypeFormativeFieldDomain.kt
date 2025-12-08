package com.mx.liftechnology.domain.model.evaluation

data class WorkTypeFormativeFieldDomain(
    val formativeFieldId: Int,
    val nameFormativeField: String,
    val listWorks: List<ListWorkFormativeFieldDomain>,
)

data class ListWorkFormativeFieldDomain(
    val workId: Int,
    val workName: String,
    val listWorks:  List<ListWorkStudentFormativeFieldDomain>,
)

data class ListWorkStudentFormativeFieldDomain(
    val workStudentId: Int,
    val workStudentName: String,
    val workStudentDate: String?,
)