package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatAssignment
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCardStudent

data class ModelRegisterAssignmentUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false),
    val subject : ModelFormatSubjectDomain? = null,
)

data class ModelRegisterAssignmentDataState (
    val nameJob : ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val listOptions: List<ModelFormatAssignment> ?= listOf(),
    val assignment: ModelFormatAssignment = ModelFormatAssignment(
        id = null,
        percent= null,
        subjectSchoolCycleGroupId = null,
        description	= "".stringToModelStateOutFieldText(),
        teacherSchoolCycleGroupId= null,
        assignmentId= null,
        assignmentName= "".stringToModelStateOutFieldText()
    ),
    val studentList: List<ModelStudentDomain>? = null,
    val studentListUI: List<ModelCustomCardStudent> = emptyList(),
)

data class ModelRegisterAssignmentDialogState(
    val date : ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val rangeDate : String? = null
)
