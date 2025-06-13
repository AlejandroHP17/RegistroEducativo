package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard

data class ModelListStudentUIState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val studentList: List<ModelStudentDomain>? = null,
    val studentListUI: List<ModelCustomCard> = emptyList()
)
