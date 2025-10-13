package com.mx.liftechnology.domain.model.subject

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Data model representing the format of an assignment in the domain layer.
 *
 * @property id The ID of the format.
 * @property percent The percentage value of the assignment.
 * @property subjectSchoolCycleGroupId The ID of the subject-school-cycle group.
 * @property description The state of the description input field.
 * @property teacherSchoolCycleGroupId The ID of the teacher-school-cycle group.
 * @property assignmentId The ID of the assignment.
 * @property assignmentName The state of the assignment name input field.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelFormatAssignment (
    val id :Int?,
    val percent:Int?,
    val subjectSchoolCycleGroupId :	Int?,
    val description	: ModelStateOutFieldText,
    val teacherSchoolCycleGroupId: Int?,
    val assignmentId: Int?,
    val assignmentName:ModelStateOutFieldText
)
