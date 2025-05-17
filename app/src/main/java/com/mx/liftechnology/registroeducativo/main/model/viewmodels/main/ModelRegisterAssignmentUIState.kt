package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCardStudent

data class ModelRegisterAssignmentUIState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false),
    val subject : ModelFormatSubjectDomain? = null,

    val nameJob : ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val date : ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val listOptions: List<String> = listOf("1","2","3","4","5","6"),
    val nameAssignment: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val studentList: List<ModelStudentDomain>? = null,
    val studentListUI: List<ModelCustomCardStudent> = emptyList(),
)
