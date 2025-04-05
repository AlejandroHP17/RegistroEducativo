package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

data class ModelCustomCardStudent(
    val id: String,
    val numberList: String?,
    val studentName: String?,
    val score: String,
    val isErrorScore: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
)
