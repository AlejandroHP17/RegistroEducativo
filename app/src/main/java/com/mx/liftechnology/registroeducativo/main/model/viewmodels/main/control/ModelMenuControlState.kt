package com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.control

import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain

data class ModelMenuControlState(
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
