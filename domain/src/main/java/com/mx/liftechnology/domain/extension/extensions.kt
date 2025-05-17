package com.mx.liftechnology.domain.extension

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

fun String?.stringToModelStateOutFieldText(isError: Boolean = false, errorMessage : String? = ""): ModelStateOutFieldText {
    return ModelStateOutFieldText(
        valueText = this?:"",
        isError = isError,
        errorMessage = errorMessage?:"")
}