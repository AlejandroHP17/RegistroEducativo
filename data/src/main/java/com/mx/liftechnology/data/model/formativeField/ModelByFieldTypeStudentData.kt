package com.mx.liftechnology.data.model.formativeField

data class ModelByFieldTypeStudentData(
    val formativeFieldId : Int,
    val formativeFieldName:String,
    val workTypeId : Int,
    val workTypeName : String,
    val works : List<ModelGetListByFieldTypeStudentData>
)

data class ModelGetListByFieldTypeStudentData(
    val workId : Int,
    val workName:String,
    val workDate : String?,
    val listStudents : List<ModelGetListByFieldStudentData>,
)

data class ModelGetListByFieldStudentData(
    val studentId : Int,
    val studentName:String,
    val grade : String?,
)
