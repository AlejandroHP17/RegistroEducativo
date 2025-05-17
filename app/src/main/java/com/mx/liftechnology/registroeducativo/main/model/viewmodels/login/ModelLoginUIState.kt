package com.mx.liftechnology.registroeducativo.main.model.viewmodels.login

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum

data class ModelLoginUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val isRemember: Boolean = false,
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false),
    val email:ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val password: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
)