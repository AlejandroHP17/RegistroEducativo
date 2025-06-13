package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import androidx.compose.ui.graphics.Color
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.registerschool.ModelSpinnerSchoolDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_success

data class ModelRegisterSchoolUIState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val read : Boolean = true,
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false),
    val buttonColor : Color = color_success,

    val schoolCycleTypeId: Int = -1,
    val spinner: ModelSpinnerSchoolDomain? = null,

    val cct: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val grade: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val group: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val cycle: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val schoolName: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val shift: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val type: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
)
