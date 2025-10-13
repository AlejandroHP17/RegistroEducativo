package com.mx.liftechnology.registroeducativo.main.model.viewmodel.login

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum

data class ModelRegisterUserStateUI(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false)
)

data class ModelRegisterUserCallbacksUI(
    val onEmailChanged: (String) -> Unit,
    val onPassChanged: (String) -> Unit,
    val onRepeatPassChanged: (String) -> Unit,
    val onCodeChanged: (String) -> Unit
)

data class ModelRegisterUserInputsUI(
    val emailInputState: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val passInputState: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val repeatPassInputState: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val codeInputState: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
)