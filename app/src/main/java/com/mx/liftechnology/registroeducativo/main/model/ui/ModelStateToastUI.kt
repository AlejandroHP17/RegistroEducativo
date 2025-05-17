package com.mx.liftechnology.registroeducativo.main.model.ui

import com.mx.liftechnology.registroeducativo.R

data class ModelStateToastUI(
    val messageToast : Int = R.string.app_name,
    val showToast : Boolean = false,
    val typeToast: ModelStateTypeToastUI = ModelStateTypeToastUI.SUCCESS,
    val secondMessageToast: String? = null
)
