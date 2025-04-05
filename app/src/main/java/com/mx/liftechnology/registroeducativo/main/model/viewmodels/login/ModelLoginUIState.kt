package com.mx.liftechnology.registroeducativo.main.model.viewmodels.login

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isRemember: Boolean = false,

    val email: ModelStateOutFieldText = ModelStateOutFieldText(
        valueText = "",
        isError = false,
        errorMessage = "",

        ),
    val password: ModelStateOutFieldText = ModelStateOutFieldText(
        valueText = "",
        isError = false,
        errorMessage = "",
        )
)