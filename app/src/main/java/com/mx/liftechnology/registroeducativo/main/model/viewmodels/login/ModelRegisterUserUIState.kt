package com.mx.liftechnology.registroeducativo.main.model.viewmodels.login

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

data class RegisterUserUiState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,

    val email: ModelStateOutFieldText =
        ModelStateOutFieldText(
            valueText = "",
            isError = false,
            errorMessage = ""
        ),
    val password: ModelStateOutFieldText =
        ModelStateOutFieldText(
            valueText = "",
            isError = false,
            errorMessage = ""
        ),
    val repeatPassword: ModelStateOutFieldText =
        ModelStateOutFieldText(
            valueText = "",
            isError = false,
            errorMessage = ""
        ),
    val code: ModelStateOutFieldText =
        ModelStateOutFieldText(
            valueText = "",
            isError = false,
            errorMessage = ""
        ),

)