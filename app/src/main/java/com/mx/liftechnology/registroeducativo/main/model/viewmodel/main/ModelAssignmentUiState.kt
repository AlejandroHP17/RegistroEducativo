package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelComplexCard

/**
 * Representa el estado de la UI para la pantalla de asignación.
 *
 * @property uiState El estado general de la UI.
 * @property subject La materia seleccionada.
 * @property student El estudiante seleccionado.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelAssignmentStateUI(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val subject: ModelFormatSubjectDomain? = null,
    val student: ModelStudentDomain? = null,
    val controlToast: ModelStateToastUI = ModelStateToastUI(R.string.app_name, false),
)

/**
 * Representa los datos de la UI para la pantalla de asignación.
 *
 * @property dataCard El modelo de la tarjeta compleja que se muestra.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelAssignmentDataState(
    val dataCard: ModelComplexCard? = null,
)

/**
 * Representa los callbacks para las interacciones de la UI en la pantalla de asignación.
 *
 * @property onExpandedTitle Lambda que se invoca al expandir o contraer el título.
 * @property onExpandedSubTitle Lambda que se invoca al expandir o contraer un subtítulo.
 * @property onItemClick Lambda que se invoca al hacer clic en un ítem.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelAssignmentUiCallbacks(
    val onExpandedTitle: (Boolean) -> Unit,
    val onExpandedSubTitle: (Boolean) -> Unit,
    val onItemClick: (ModelComplexCard?) -> Unit,
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