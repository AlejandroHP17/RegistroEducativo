package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard

data class ModelListStudentUIState(
    val showControl: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val studentList: List<ModelStudentDomain>? = null,
    val studentListUI: List<ModelCustomCard> = emptyList()
)
