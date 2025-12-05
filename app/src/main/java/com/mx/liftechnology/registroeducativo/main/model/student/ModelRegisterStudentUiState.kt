package com.mx.liftechnology.registroeducativo.main.model.student

import androidx.compose.ui.graphics.Color
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess

/**
 * Representa el estado de la UI para la pantalla de registro de estudiantes.
 *
 * @property uiState El estado general de la UI.
 * @property name El estado del campo de entrada del nombre.
 * @property lastName El estado del campo de entrada del apellido paterno.
 * @property secondLastName El estado del campo de entrada del apellido materno.
 * @property curp El estado del campo de entrada de la CURP.
 * @property birthday El estado del campo de entrada de la fecha de nacimiento.
 * @property phoneNumber El estado del campo de entrada del número de teléfono.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @property buttonColor El color del botón de grabación.
 * @author Pelkidev
 * @version 1.0.0
 */
data class RegisterStudentUiInputs(
    val studentId : Int? = null,
    val name: ModelStateOutFieldText = ModelStateOutFieldText(),
    val lastName: ModelStateOutFieldText = ModelStateOutFieldText(),
    val secondLastName: ModelStateOutFieldText = ModelStateOutFieldText(),
    val curp: ModelStateOutFieldText = ModelStateOutFieldText(),
    val birthday: ModelStateOutFieldText = ModelStateOutFieldText(),
    val phoneNumber: ModelStateOutFieldText = ModelStateOutFieldText(),

    )

data class RegisterStudentUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val isNew: Boolean = true,
    val controlToast: ToastUiState = ToastUiState(R.string.app_name, false),
    val buttonColor: Color = colorSuccess
)

/**
 * Representa los callbacks para las interacciones de la UI en la pantalla de registro de estudiantes.
 *
 * @property onNameChanged Lambda que se invoca al cambiar el nombre.
 * @property onLastNameChanged Lambda que se invoca al cambiar el apellido paterno.
 * @property onSecondLastNameChanged Lambda que se invoca al cambiar el apellido materno.
 * @property onCurpChanged Lambda que se invoca al cambiar la CURP.
 * @property onPhoneNumberChanged Lambda que se invoca al cambiar el número de teléfono.
 * @property onBirthdayChanged Lambda que se invoca al cambiar la fecha de nacimiento.
 * @author Pelkidev
 * @version 1.0.0
 */
data class RegisterStudentUiCallbacks(
    val onNameChanged: (ModelStateOutFieldText) -> Unit,
    val onLastNameChanged: (ModelStateOutFieldText) -> Unit,
    val onSecondLastNameChanged: (ModelStateOutFieldText) -> Unit,
    val onCurpChanged: (ModelStateOutFieldText) -> Unit,
    val onPhoneNumberChanged: (ModelStateOutFieldText) -> Unit,
    val onBirthdayChanged: () -> Unit
)
