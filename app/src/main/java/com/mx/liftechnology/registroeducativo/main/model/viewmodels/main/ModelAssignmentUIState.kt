package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain

data class ModelAssignmentUIState(
    val isLoading: Boolean = false,
    val subject : ModelFormatSubjectDomain? = null
)
