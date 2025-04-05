package com.mx.liftechnology.registroeducativo.main.model.viewmodels.login

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.main.viewextensions.stringToModelStateOutFieldText

data class RegisterUserUiState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,

    val email: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val password: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val repeatPassword: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val code: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),

)