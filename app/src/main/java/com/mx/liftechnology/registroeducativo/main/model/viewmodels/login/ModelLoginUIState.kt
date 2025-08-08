package com.mx.liftechnology.registroeducativo.main.model.viewmodels.login

import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum

data class ModelLoginUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val isRemember: Boolean = false,
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false)
)

data class LoginCallbacks(
    val onEmailChanged: (String) -> Unit,
    val onPassChanged: (String) -> Unit,
    val onRememberChanged: (Boolean) -> Unit
)