package com.mx.liftechnology.domain.model.subject

data class ModelSpinnersWorkMethods(
    val position:Int,
    var name: String?,
    var percent: String?,
    val assessmentTypeId : Int?,
    val teacherSchoolCycleGroupId : Int?
)