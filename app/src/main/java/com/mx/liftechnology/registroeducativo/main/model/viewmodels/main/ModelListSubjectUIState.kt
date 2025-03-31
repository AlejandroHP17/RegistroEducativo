package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard

data class ModelListSubjectUIState(
    val showControl: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val subjectList: List<ModelFormatSubjectDomain>? = null,
    val subjectListUI: List<ModelCustomCard> = emptyList()
)
