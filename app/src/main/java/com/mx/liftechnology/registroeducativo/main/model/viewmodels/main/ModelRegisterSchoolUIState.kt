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
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false),
    val buttonColor : Color = color_success,
)

data class ModelRegisterSchoolUISemiAutomaticData(
    val schoolName: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val shift: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val type: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val schoolCycleTypeId: Int = -1,
    val spinner: ModelSpinnerSchoolDomain? = null,
    val read : Boolean = true,
)

data class ModelRegisterSchoolUICallbacks(
    val onCycleChanged: (String) -> Unit,
    val onGradeChanged: (String) -> Unit,
    val onGroupChanged: (String) -> Unit,
)
