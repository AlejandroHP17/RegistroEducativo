package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomSpinner
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.toModelCustomSpinner

data class ModelRegisterSubjectUIState(
    val showControl: Boolean = false,
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val read: Boolean = false,
    val controlToast: ModelStateToastUI = ModelStateToastUI(R.string.app_name, false),

    val subject: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val options: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val listOptions: List<ModelCustomSpinner> =
        listOf(
            "1".toModelCustomSpinner(),
            "2".toModelCustomSpinner(),
            "3".toModelCustomSpinner(),
            "4".toModelCustomSpinner(),
            "5".toModelCustomSpinner(),
            "6".toModelCustomSpinner(),
            "7".toModelCustomSpinner(),
            "8".toModelCustomSpinner(),
            "9".toModelCustomSpinner()
        ),
    val listAdapter: List<ModelSpinnersWorkMethods>? = null,
    val listWorkMethods: List<ResponseGetListAssessmentType?> = emptyList(),

    )


