package com.mx.liftechnology.registroeducativo.main.model

import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI

data class ModelShareUIState(
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false)
)
