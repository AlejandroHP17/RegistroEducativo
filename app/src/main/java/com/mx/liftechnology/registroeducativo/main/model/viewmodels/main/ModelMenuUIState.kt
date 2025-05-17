package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum

data class ModelMenuUIState(
    val showControl: Boolean = false,
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast : ModelStateToastUI = ModelStateToastUI(R.string.app_name,false),
    val evaluationItems: List<ModelPrincipalMenuData> = listOf(),
    val controlItems: List<ModelPrincipalMenuData> = listOf(),
    val studentGroupItem: ModelDialogStudentGroupDomain = ModelDialogStudentGroupDomain(
        selected = false,
        item = null,
        nameItem = null,
        listItemPartial = null,
        itemPartial = null,
        namePartial = null
    ),
    val studentGroupList: List<ModelDialogStudentGroupDomain> = listOf(
        ModelDialogStudentGroupDomain(
            selected = false,
            item = null,
            nameItem = null,
            listItemPartial = null,
            itemPartial = null,
            namePartial = null
        )
    ),
)
