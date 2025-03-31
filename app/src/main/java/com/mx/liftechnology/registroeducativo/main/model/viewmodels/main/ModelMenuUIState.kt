package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main

import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain

data class ModelMenuUIState(
    val showControl: Boolean = false,
    val isLoading: Boolean = false,
    val evaluationItems: List<ModelPrincipalMenuData> = listOf(),
    val controlItems: List<ModelPrincipalMenuData> = listOf(),
    val showDialog: Boolean = false,
    val studentGroupItem : ModelDialogStudentGroupDomain = ModelDialogStudentGroupDomain(
        selected = false,
        item = null,
        nameItem = null
    ),
    val studentGroupList : List<ModelDialogStudentGroupDomain> = listOf( ModelDialogStudentGroupDomain(
        selected = false,
        item = null,
        nameItem = null
    ))
)
