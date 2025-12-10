package com.mx.liftechnology.registroeducativo.main.model.menu

import com.mx.liftechnology.domain.model.schoolCycle.PrincipalMenuDomain
import com.mx.liftechnology.domain.model.schoolCycle.DialogStudentGroupDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi

/**
 * Representa el estado de la UI de la pantalla de menú.
 *
 * @property showControl Indica si el área de control debe mostrarse.
 * @property uiState El estado actual de la UI (por ejemplo, cargando, error, éxito).
 * @property controlToast El estado para mostrar mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class MenuUiState(
    val showControl: Boolean = false,
    val uiState: EnumUi = EnumUi.NOTHING,
    val controlToast : ToastUiState = ToastUiState(R.string.app_name,false),
)

/**
 * Representa el estado para los diálogos en la pantalla de menú.
 *
 * @property studentGroupItem El ítem de grupo de estudiantes actualmente seleccionado.
 * @property studentGroupList La lista de grupos de estudiantes disponibles.
 * @author Pelkidev
 * @version 1.0.0
 */
data class MenuUiDialog(
    val studentGroupItem: DialogStudentGroupDomain = DialogStudentGroupDomain(
        selected = false,
        item = null,
        nameItem = null,
        listItemPartial = null,
        itemPartial = null,
        namePartial = null
    ),
    val studentGroupList: List<DialogStudentGroupDomain> = listOf(
        DialogStudentGroupDomain(
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
 * Representa los datos mostrados en la pantalla de menú.
 *
 * @property evaluationItems La lista de ítems para el área de evaluación.
 * @property controlItems La lista de ítems para el área de control.
 * @author Pelkidev
 * @version 1.0.0
 */
data class MenuUiData (
    val evaluationItems: List<PrincipalMenuDomain> = listOf(),
    val controlItems: List<PrincipalMenuDomain> = listOf(),
)
