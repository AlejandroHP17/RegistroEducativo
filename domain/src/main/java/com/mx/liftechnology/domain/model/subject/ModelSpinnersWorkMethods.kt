package com.mx.liftechnology.domain.model.subject

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

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