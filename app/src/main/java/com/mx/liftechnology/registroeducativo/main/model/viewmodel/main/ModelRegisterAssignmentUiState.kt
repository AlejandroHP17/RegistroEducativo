package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCardStudent

data class RegisterAssignmentUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast : ToastUiState = ToastUiState(R.string.app_name,false),
    val formativeField : ModelFormatFormativeFieldsDomain? = null,
)

data class RegisterAssignmentUiData (
    val nameJob : ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val date : ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val listOptions: List<ModelCustomSpinner> ?= listOf(),
    val options: ModelCustomSpinner? = null,


    val nameAssignment: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val studentList: List<ModelStudentDomain>? = null,
    val studentListUI: List<ModelCustomCardStudent> = emptyList(),
)