package com.mx.liftechnology.registroeducativo.main.model.evaluation

import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.student.StudentDomainPar
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.share.ModelCustomCardStudent

data class RegisterEvaluationUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast : ToastUiState = ToastUiState(R.string.app_name,false),
    val formativeField : FormativeFieldDomainPar? = null,
)

data class RegisterEvaluationUiData (
    val nameJob : ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val date : ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val listOptions: List<ModelCustomSpinner> ?= listOf(),
    val options: ModelCustomSpinner? = null,


    val nameAssignment: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val studentList: List<StudentDomainPar>? = null,
    val studentListUI: List<ModelCustomCardStudent> = emptyList(),
)