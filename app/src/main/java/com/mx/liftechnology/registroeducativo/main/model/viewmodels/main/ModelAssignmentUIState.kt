package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum

data class ModelAssignmentUIState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val subject : ModelFormatSubjectDomain? = null
)
