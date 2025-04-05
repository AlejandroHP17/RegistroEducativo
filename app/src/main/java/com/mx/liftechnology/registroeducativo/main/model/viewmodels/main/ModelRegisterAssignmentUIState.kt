package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCardStudent

data class ModelRegisterAssignmentUIState(
    val isLoading: Boolean = false,
    val subject : ModelFormatSubjectDomain? = null,

    val nameJob : String = "",
    val date : String = "",
    val listOptions: List<String> = listOf("1","2","3","4","5","6"),
    val nameAssignment: String = "",
    val studentList: List<ModelStudentDomain>? = null,
    val studentListUI: List<ModelCustomCardStudent> = emptyList(),

    val isErrorNameJob: ModelStateOutFieldText =
        ModelStateOutFieldText(
            isError = false,
            errorMessage = ""
        ),
    val isErrorDate: ModelStateOutFieldText =
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
