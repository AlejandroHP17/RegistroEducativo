package com.mx.liftechnology.domain.model.subject

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

data class ModelSpinnersWorkMethods(
    val position:Int,
    var name: String?,
    var percent: String?,
    val assessmentTypeId : Int?,
    val teacherSchoolCycleGroupId : Int?,
    val isErrorName: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorPercent: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
)