package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText

data class ModelCustomCardStudent(
    val id: String,
    val numberList: String?,
    val studentName: String?,
    val score: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
)
