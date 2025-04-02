package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import androidx.compose.ui.graphics.Color
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.registerschool.ModelSpinnerSchoolDomain
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_success

data class ModelRegisterSchoolUIState(
    val isLoading: Boolean = false,
    val read : Boolean = true,
    val isSuccess : Boolean = false,
    val buttonColor : Color = color_success,

    val cct: String = "",
    val schoolName: String = "",
    val schoolCycleTypeId: Int = -1,
    val shift: String = "",
    val type: String = "",

    val cycle: String = "",
    val grade: String = "",
    val group: String = "",
    val spinner: ModelSpinnerSchoolDomain? = null,

    val isErrorCct: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorGrade: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorGroup: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorCycle: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorGeneric: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        )
)
