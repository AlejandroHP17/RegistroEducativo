package com.mx.liftechnology.domain.extension

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Extension function to convert a nullable String to a [ModelStateOutFieldText].
 *
 * @receiver The nullable String to convert.
 * @param isError Indicates whether the state represents an error.
 * @param errorMessage The error message, if any.
 * @return A [ModelStateOutFieldText] object.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun String?.stringToModelStateOutFieldText(isError: Boolean = false, errorMessage : String? = ""): ModelStateOutFieldText {
    return ModelStateOutFieldText(
        valueText = this?:"",
        isError = isError,
        errorMessage = errorMessage?:""
    )
}