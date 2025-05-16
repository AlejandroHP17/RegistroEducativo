package com.mx.liftechnology.registroeducativo.main.model.viewmodels.login

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.main.viewextensions.stringToModelStateOutFieldText

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isRemember: Boolean = false,

    val email:ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val password: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
)