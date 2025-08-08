package com.mx.liftechnology.registroeducativo.main.model.viewmodels.login

import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum

data class ModelRegisterUserUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false)
)

data class ModelRegisterUserUICallbacks(
    val onEmailChanged: (String) -> Unit,
    val onPassChanged: (String) -> Unit,
    val onRepeatPassChanged: (String) -> Unit,
    val onCodeChanged: (String) -> Unit
)