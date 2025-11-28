package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard

/**
 * Representa el estado de la UI para la pantalla de lista de estudiantes.
 *
 * @property uiState El estado general de la UI.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ListStudentUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
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
    val studentList: List<ModelStudentDomain>? = null,
    val studentListUI: List<ModelCustomCard> = emptyList()
)
