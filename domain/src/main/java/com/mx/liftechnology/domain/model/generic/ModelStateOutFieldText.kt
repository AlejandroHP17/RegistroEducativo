package com.mx.liftechnology.domain.model.generic

data class ModelStateOutFieldText(
    val valueText : String = "",
    val isError: Boolean = false,
    val errorMessage: String = ""
)
