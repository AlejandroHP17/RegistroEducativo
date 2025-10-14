package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import androidx.compose.ui.graphics.Color
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomSpinner
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess

/**
 * Representa el estado de la UI para la pantalla de registro de escuela.
 *
 * @property uiState El estado general de la UI.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @property buttonColor El color del botón de grabación.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelRegisterSchoolUIState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast: ModelStateToastUI = ModelStateToastUI(R.string.app_name, false),
    val buttonColor: Color = colorSuccess
)

/**
 * Representa los datos semi-automáticos de la UI para la pantalla de registro de escuela.
 *
 * @property schoolName El nombre de la escuela.
 * @property shift El turno de la escuela.
 * @property type El tipo de ciclo escolar.
 * @property schoolCycleTypeId El ID del tipo de ciclo escolar.
 * @property spinner El modelo para los spinners.
 * @property read Indica si los campos son de solo lectura.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelRegisterSchoolUISemiAutomaticData(
    val schoolName: ModelStateOutFieldText = ModelStateOutFieldText(),
    val shift: ModelStateOutFieldText = ModelStateOutFieldText(),
    val type: ModelStateOutFieldText = ModelStateOutFieldText(),
    val schoolCycleTypeId: Int = -1,
    val spinner: ModelSpinnerSchoolUi? = null,
    val read: Boolean = true,
)

/**
 * Representa los callbacks para las interacciones de la UI en la pantalla de registro de escuela.
 *
 * @property onCycleChanged Lambda que se invoca al cambiar el ciclo.
 * @property onGradeChanged Lambda que se invoca al cambiar el grado.
 * @property onGroupChanged Lambda que se invoca al cambiar el grupo.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelRegisterSchoolUICallbacks(
    val onCycleChanged: (String) -> Unit,
    val onGradeChanged: (String) -> Unit,
    val onGroupChanged: (String) -> Unit
)

data class ModelSpinnerSchoolUi(
    val cycle: List<ModelCustomSpinner>?,
    val grade: List<ModelCustomSpinner>?,
    val group: List<ModelCustomSpinner>?
)
