package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelComplexCard

data class ModelAssignmentUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false),
    val subject : ModelFormatSubjectDomain? = null,
    val student : ModelStudentDomain? = null
)

data class ModelAssignmentDataState(
    val subject: ModelFormatSubjectDomain? = null,
    val dataCard: ModelComplexCard? = null,
)

data class ModelAssignmentUiCallbacks(
    val onExpandedTitle: (Boolean) -> Unit,
    val onExpandedSubTitle: (Boolean) -> Unit,
    val onItemClick : (ModelComplexCard?) -> Unit
)

fun ModelFormatSubjectDomain?.toModelComplexCard(): ModelComplexCard {
    return ModelComplexCard(
        idTitle = this?.subjectId,
        nameTitle = this?.name,
        isShowTitle = true,
        isExpandedTitle = true,
        list = null,
    )
}