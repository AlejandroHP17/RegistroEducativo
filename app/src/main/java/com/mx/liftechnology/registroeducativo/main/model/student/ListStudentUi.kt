package com.mx.liftechnology.registroeducativo.main.model.student

import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCard

/**
 * Representa el estado de la UI para la pantalla de lista de estudiantes.
 *
 * @property uiState El estado general de la UI.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ListStudentUiState(
    val uiState: EnumUi = EnumUi.NOTHING,
    val controlToast: ToastUiState = ToastUiState(R.string.app_name, false),
)

/**
 * Representa los datos de la UI para la pantalla de lista de estudiantes.
 *
 * @property studentList La lista de estudiantes.
 * @property studentListUI La lista de estudiantes formateada para la UI.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ListStudentUiData(
    val studentList: List<StudentDomainPar>? = null,
    val studentListUI: List<CustomCard> = emptyList()
)
