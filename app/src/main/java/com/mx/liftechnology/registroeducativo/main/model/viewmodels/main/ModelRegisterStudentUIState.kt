package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import androidx.compose.ui.graphics.Color
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_success

data class ModelRegisterStudentUIState(
    val isLoading: Boolean = false,
    val isSuccess : Boolean = false,
    val buttonColor : Color = color_success,

    val name: String = "",
    val lastName: String = "",
    val secondLastName: String = "",
    val curp: String = "",
    val birthday: String = "",
    val phoneNumber: String = "",

    val isErrorName: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorLastName: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorSecondLastName: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorCurp: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorBirthday: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorPhoneNumber: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        )
)
