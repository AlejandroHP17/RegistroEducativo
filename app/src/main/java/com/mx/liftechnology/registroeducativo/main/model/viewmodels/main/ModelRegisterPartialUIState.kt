package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

data class ModelRegisterPartialUIState(
    val showControl: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,

    val isErrorOption: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
)


