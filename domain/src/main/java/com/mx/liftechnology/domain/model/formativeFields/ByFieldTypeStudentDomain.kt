package com.mx.liftechnology.domain.model.formativeFields

data class ByFieldTypeStudentDomain(
    val formativeFieldId : Int,
    val formativeFieldName:String,
    val workTypeId : Int,
    val workTypeName : String,
    val works : List<GetListByFieldTypeStudentDomain>
)

data class GetListByFieldTypeStudentDomain(
    val workId : Int,
    val workName:String,
    val workDate : String?,
    val listStudents : List<GetListByFieldStudentDomain>,
)

data class GetListByFieldStudentDomain(
    val studentId : Int,
    val studentName:String,
    val grade : String?,
)
