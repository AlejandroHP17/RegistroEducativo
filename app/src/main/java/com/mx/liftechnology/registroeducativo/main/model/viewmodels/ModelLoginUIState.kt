package com.mx.liftechnology.registroeducativo.main.model.viewmodels

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isErrorEmail: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorPass: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isSuccess: Boolean = false,
    val isRemember: Boolean = false
)