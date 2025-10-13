package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import androidx.compose.ui.graphics.Color
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomSpinner
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess

data class ModelRegisterSchoolUIState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false),
    val buttonColor : Color = colorSuccess,
)

data class ModelRegisterSchoolUISemiAutomaticData(
    val schoolName: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val shift: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val type: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val schoolCycleTypeId: Int = -1,
    val spinner: ModelSpinnerSchoolUi? = null,
    val read : Boolean = true,
)

data class ModelRegisterSchoolUICallbacks(
    val onCycleChanged: (String) -> Unit,
    val onGradeChanged: (String) -> Unit,
    val onGroupChanged: (String) -> Unit,
)

data class ModelSpinnerSchoolUi(
    val cycle: List<ModelCustomSpinner>?,
    val grade: List<ModelCustomSpinner>?,
    val group: List<ModelCustomSpinner>?
)
