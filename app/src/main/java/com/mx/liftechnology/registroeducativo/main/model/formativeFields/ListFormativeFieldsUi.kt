package com.mx.liftechnology.registroeducativo.main.model.formativeFields

import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCard

/**
 * Representa el estado de la UI para la pantalla de lista de materias.
 *
 * @property uiState El estado general de la UI.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ListFormativeFieldsUiState(
    val uiState: EnumUi = EnumUi.NOTHING,
    val controlToast: ToastUiState = ToastUiState(R.string.app_name, false),
)

/**
 * Representa los datos de la UI para la pantalla de lista de materias.
 *
 * @property formativeFieldsList La lista de materias.
 * @property formativeFieldsListUI La lista de materias formateada para la UI.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ListFormativeFieldsUiData(
    val formativeFieldsList: List<FormativeFieldDomainPar>? = null,
    val formativeFieldsListUI: List<CustomCard> = emptyList()
)
