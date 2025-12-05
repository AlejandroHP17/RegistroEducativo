package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelSubComplexCard

/**
 * Representa el estado de la UI para la pantalla de asignación.
 *
 * @property uiState El estado general de la UI.
 * @property formativeFields La materia seleccionada.
 * @property student El estudiante seleccionado.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class WotyFofiUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val formativeFields: FormativeFieldDomainPar? = null,
    val student: StudentDomain? = null,
    val controlToast: ToastUiState = ToastUiState(R.string.app_name, false),
)

/**
 * Representa los datos de la UI para la pantalla de asignación.
 *
 * @property dataCard El modelo de la tarjeta compleja que se muestra.
 * @author Pelkidev
 * @version 1.0.0
 */
data class WotyFofiUiData(
    val dataCard: List<ModelComplexCard>? = null,
)

/**
 * Representa los callbacks para las interacciones de la UI en la pantalla de asignación.
 *
 * @property onExpandedTitle Lambda que se invoca al expandir o contraer el título.
 * @property onExpandedSubTitle Lambda que se invoca al expandir o contraer un subtítulo.
 * @author Pelkidev
 * @version 1.0.0
 */
data class WotyFofiUiCallbacks(
    val onExpandedTitle:  (ModelComplexCard) -> Unit,
    val onExpandedSubTitle: (ModelSubComplexCard, ModelComplexCard)-> Unit,
)

fun FormativeFieldDomainPar?.toModelComplexCard(): ModelComplexCard {
    return ModelComplexCard(
        idTitle = this?.formativeFieldId,
        nameTitle = this?.name,
        isShowTitle = true,
        isExpandedTitle = true,
        list = null,
    )
}