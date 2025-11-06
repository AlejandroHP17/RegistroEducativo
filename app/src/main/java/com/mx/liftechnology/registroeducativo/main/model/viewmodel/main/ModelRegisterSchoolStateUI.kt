package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import androidx.compose.ui.graphics.Color
import com.mx.liftechnology.data.model.ModelCCTDataPeriodCatalog
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
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
data class ModelRegisterSchoolStateUI(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast: ModelStateToastUI = ModelStateToastUI(R.string.app_name, false),
    val buttonColor: Color = colorSuccess
)

/**
 * Representa los datos semi-automáticos de la UI para la pantalla de registro de escuela.
 *
 * @property schoolName El nombre de la escuela.
 * @property shiftName El turno de la escuela.
 * @property type El tipo de ciclo escolar.
 * @property spinner El modelo para los spinners.
 * @property read Indica si los campos son de solo lectura.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelRegisterSchoolUISemiAutomaticData(
    val schoolName: ModelStateOutFieldText = ModelStateOutFieldText(),
    val schoolId: Int = 0,
    val shiftName: ModelStateOutFieldText = ModelStateOutFieldText(),
    val periodCatalog:List<ModelCCTDataPeriodCatalog>? = null,
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
    val onTypeChanged: (ModelCustomSpinner) -> Unit,
    val onCycleChanged: (ModelCustomSpinner) -> Unit,
    val onGradeChanged: (ModelCustomSpinner) -> Unit,
    val onGroupChanged: (ModelCustomSpinner) -> Unit
)

/**
 * Modelo de datos que contiene las listas de opciones para los spinners del formulario de registro de escuela.
 *
 * @property cycle La lista de opciones para el spinner de ciclo escolar.
 * @property grade La lista de opciones para el spinner de grado.
 * @property group La lista de opciones para el spinner de grupo.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelSpinnerSchoolUi(
    val type: List<ModelCustomSpinner>?,
    val cycle: List<ModelCustomSpinner>?,
    val grade: List<ModelCustomSpinner>?,
    val group: List<ModelCustomSpinner>?
)

/**
 * Representa el estado de los campos de entrada del usuario en el formulario de registro de escuela.
 *
 * @property cct El estado del campo de texto para la Clave de Centro de Trabajo (CCT).
 * @property grade El estado que almacena el valor del grado seleccionado en el spinner.
 * @property group El estado que almacena el valor del grupo seleccionado en el spinner.
 * @property cycle El estado que almacena el valor del ciclo seleccionado en el spinner.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelRegisterSchoolInputsUI(
    val cct: ModelStateOutFieldText = ModelStateOutFieldText(),
    val type: ModelStateOutFieldText = ModelStateOutFieldText(),
    val grade: ModelStateOutFieldText = ModelStateOutFieldText(),
    val group: ModelStateOutFieldText = ModelStateOutFieldText(),
    val cycle: ModelStateOutFieldText = ModelStateOutFieldText()
)
