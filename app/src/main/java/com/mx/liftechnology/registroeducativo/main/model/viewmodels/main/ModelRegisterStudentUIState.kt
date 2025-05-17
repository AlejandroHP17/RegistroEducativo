package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import androidx.compose.ui.graphics.Color
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_success
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText

data class ModelRegisterStudentUIState(
    val isLoading: Boolean = false,
    val isSuccess : Boolean = false,
    val buttonColor : Color = color_success,

    val name: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val lastName: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val secondLastName:ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val curp: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val birthday :ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val phoneNumber:ModelStateOutFieldText = "".stringToModelStateOutFieldText(),

)
