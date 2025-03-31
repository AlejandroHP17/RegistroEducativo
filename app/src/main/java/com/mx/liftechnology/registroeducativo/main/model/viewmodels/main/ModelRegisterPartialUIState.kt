package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

data class ModelRegisterPartialUIState(
    val showControl: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val listOptions: List<String> = listOf("1","2","3","4","5","6"),
    val listCalendar: List<String>? = null,
    val numberPartials: String = "",
    val read : Boolean = false,
    val isErrorSubject: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),

    val isErrorOption: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
)


