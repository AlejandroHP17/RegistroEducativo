package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import androidx.compose.ui.graphics.Color
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.registerschool.ModelSpinnerSchoolDomain
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_success
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText

data class ModelRegisterSchoolUIState(
    val isLoading: Boolean = false,
    val read : Boolean = true,
    val isSuccess : Boolean = false,
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
