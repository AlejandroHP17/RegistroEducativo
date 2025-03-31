package com.mx.liftechnology.registroeducativo.main.model.viewmodels.login

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

data class RegisterUserUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val code: String = "",
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
    val isErrorRepeatPass: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorCode: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isSuccess: Boolean = false
)