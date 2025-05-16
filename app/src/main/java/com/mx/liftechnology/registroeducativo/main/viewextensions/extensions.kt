package com.mx.liftechnology.registroeducativo.main.viewextensions

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

fun String?.stringToModelStateOutFieldText(): ModelStateOutFieldText {
    return ModelStateOutFieldText(
        valueText = this?:"",
        isError = false,
        errorMessage = "")
}