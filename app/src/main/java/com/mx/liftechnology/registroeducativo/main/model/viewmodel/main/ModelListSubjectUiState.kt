package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard

/**
 * Representa el estado de la UI para la pantalla de lista de materias.
 *
 * @property uiState El estado general de la UI.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelListSubjectStateUI(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast: ModelStateToastUI = ModelStateToastUI(R.string.app_name, false),
)

/**
 * Representa los datos de la UI para la pantalla de lista de materias.
 *
 * @property subjectList La lista de materias.
 * @property subjectListUI La lista de materias formateada para la UI.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelListSubjectDataState(
    val subjectList: List<ModelFormatSubjectDomain>? = null,
    val subjectListUI: List<ModelCustomCard> = emptyList()
)
