package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import com.mx.liftechnology.data.model.schoolCycle.ModelPrincipalMenuData
import com.mx.liftechnology.domain.model.schoolCycle.ModelDialogStudentGroupDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum

/**
 * Represents the state of the Menu screen's UI.
 *
 * @property showControl Indicates whether the control area should be shown.
 * @property uiState The current state of the UI (e.g., loading, error, success).
 * @property controlToast The state for displaying toast messages.
 * @author Pelkidev
 * @version 1.0.0
 */
data class MenuUiState(
    val showControl: Boolean = false,
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast : ToastUiState = ToastUiState(R.string.app_name,false),
)

/**
 * Represents the state for dialogs on the Menu screen.
 *
 * @property studentGroupItem The currently selected student group item.
 * @property studentGroupList The list of available student groups.
 * @author Pelkidev
 * @version 1.0.0
 */
data class MenuUiDialog(
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
    )
)

/**
 * Represents the data displayed on the Menu screen.
 *
 * @property evaluationItems The list of items for the evaluation area.
 * @property controlItems The list of items for the control area.
 * @author Pelkidev
 * @version 1.0.0
 */
data class MenuUiData (
    val evaluationItems: List<ModelPrincipalMenuData> = listOf(),
    val controlItems: List<ModelPrincipalMenuData> = listOf(),
)
