package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard

data class ModelListSubjectUiState(
    val showControl: Boolean = false,
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING
)

data class ModelListSubjectDataState(
    val subjectList: List<ModelFormatSubjectDomain>? = null,
    val subjectListUI: List<ModelCustomCard> = emptyList()
)
