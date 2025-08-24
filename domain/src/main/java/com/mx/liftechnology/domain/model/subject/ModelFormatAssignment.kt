package com.mx.liftechnology.domain.model.subject

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

data class ModelFormatAssignment (
    val id :Int?,
    val percent:Int?,
    val subjectSchoolCycleGroupId :	Int?,
    val description	: ModelStateOutFieldText,
    val teacherSchoolCycleGroupId: Int?,
    val assignmentId: Int?,
    val assignmentName:ModelStateOutFieldText
)

