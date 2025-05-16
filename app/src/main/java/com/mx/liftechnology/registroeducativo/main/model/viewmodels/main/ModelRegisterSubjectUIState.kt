package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.core.network.callapi.ResponseGetListAssessmentType
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods
import com.mx.liftechnology.registroeducativo.main.viewextensions.stringToModelStateOutFieldText

data class ModelRegisterSubjectUIState(
    val showControl: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val read : Boolean = false,

    val subject: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val options: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val listOptions: List<String> = listOf("1","2","3","4","5","6","7","8","9"),
    val listAdapter: List<ModelSpinnersWorkMethods>? = null,
    val listWorkMethods: List<ResponseGetListAssessmentType?> = emptyList(),

)


