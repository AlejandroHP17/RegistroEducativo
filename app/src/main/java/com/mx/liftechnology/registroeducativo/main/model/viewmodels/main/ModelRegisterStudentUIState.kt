package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import androidx.compose.ui.graphics.Color
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess

data class ModelRegisterStudentUIState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val buttonColor : Color = colorSuccess,
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false),

    val name: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val lastName: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val secondLastName:ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val curp: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val birthday :ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val phoneNumber:ModelStateOutFieldText = "".stringToModelStateOutFieldText(),

    )
