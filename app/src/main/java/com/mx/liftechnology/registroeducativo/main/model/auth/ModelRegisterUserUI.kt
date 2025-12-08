package com.mx.liftechnology.registroeducativo.main.model.auth

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum

/**
 * Representa los estados de la UI para la pantalla de registro de usuario.
 *
 * @property uiState El estado general de la UI (cargando, éxito, error, etc.).
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class RegisterUserUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast: ToastUiState = ToastUiState(R.string.app_name, false),
)

/**
 * Representa los campos de entrada de la UI para la pantalla de registro de usuario.
 *
 * @property emailInputState El estado del campo de entrada del email.
 * @property passInputState El estado del campo de entrada de la contraseña.
 * @property repeatPassInputState El estado del campo de entrada para repetir la contraseña.
 * @property codeInputState El estado del campo de entrada del código de activación.
 * @author Pelkidev
 * @version 1.0.0
 */
data class RegisterUserUiInputs(
    val emailInputState: ModelStateOutFieldText = ModelStateOutFieldText(),
    val passInputState: ModelStateOutFieldText = ModelStateOutFieldText(),
    val repeatPassInputState: ModelStateOutFieldText = ModelStateOutFieldText(),
    val codeInputState: ModelStateOutFieldText = ModelStateOutFieldText(),
)

/**
 * Representa los callbacks para las interacciones de la UI en la pantalla de registro de usuario.
 *
 * @property onEmailChanged Lambda que se invoca al cambiar el email.
 * @property onPassChanged Lambda que se invoca al cambiar la contraseña.
 * @property onRepeatPassChanged Lambda que se invoca al cambiar la repetición de la contraseña.
 * @property onCodeChanged Lambda que se invoca al cambiar el código de activación.
 * @author Pelkidev
 * @version 1.0.0
 */
data class RegisterUserUiCallbacks(
    val onEmailChanged: (ModelStateOutFieldText) -> Unit,
    val onPassChanged: (ModelStateOutFieldText) -> Unit,
    val onRepeatPassChanged: (ModelStateOutFieldText) -> Unit,
    val onCodeChanged: (ModelStateOutFieldText) -> Unit
)
