package com.mx.liftechnology.domain.model.subject

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Data model for spinners in the work methods section.
 *
 * @property position The position of the item in a list.
 * @property assessmentTypeId The ID of the assessment type.
 * @property teacherSchoolCycleGroupId The ID of the teacher-school-cycle group.
 * @property name The state of the name input field.
 * @property percent The state of the percent input field.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelSpinnersWorkMethods(
    val position:Int,
    val assessmentTypeId : Int?,
    val teacherSchoolCycleGroupId : Int?,
    val name: ModelStateOutFieldText =
        ModelStateOutFieldText(
            valueText = "",
            isError = false,
            errorMessage = ""
        ),
    val percent: ModelStateOutFieldText =
        ModelStateOutFieldText(
            valueText = "",
            isError = false,
            errorMessage = ""
        ),
)