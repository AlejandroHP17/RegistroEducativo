package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomSpinner
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.toModelCustomSpinner

data class ModelRegisterPartialUIState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast: ModelStateToastUI = ModelStateToastUI(R.string.app_name, false),
    val isAvailable: Boolean = true,
)

data class ModelRegisterPartialUIData(
    val listOptions: List<ModelCustomSpinner> =
        listOf(
            "1".toModelCustomSpinner(),
            "2".toModelCustomSpinner(),
            "3".toModelCustomSpinner(),
            "4".toModelCustomSpinner(),
            "5".toModelCustomSpinner(),
            "6".toModelCustomSpinner()
        ),
    val listCalendar: List<ModelDatePeriodDomain>? = null,
    val numberPartials: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val read: Boolean = false,
)

