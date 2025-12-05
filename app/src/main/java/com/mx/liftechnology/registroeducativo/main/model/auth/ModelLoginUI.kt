package com.mx.liftechnology.registroeducativo.main.model.auth

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum

/**
 * Representa los estados de la UI para la pantalla de login.
 *
 * @property uiState El estado general de la UI (cargando, éxito, error, etc.).
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class LoginUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val controlToast: ToastUiState = ToastUiState(R.string.app_name, false),
)

/**
 * Representa los campos de entrada de la UI para la pantalla de login.
 *
 * @property emailInputState El estado del campo de entrada del email.
 * @property passInputState El estado del campo de entrada de la contraseña.
 * @property isRemember Indica si la opción "recordarme" está activada.
 * @author Pelkidev
 * @version 1.0.0
 */
data class LoginUiInputs(
    val emailInputState: ModelStateOutFieldText = ModelStateOutFieldText(),
    val passInputState: ModelStateOutFieldText = ModelStateOutFieldText(),
    val isRemember: Boolean = false,
)

/**
 * Representa los callbacks para las interacciones de la UI en la pantalla de login.
 *
 * @property onEmailChanged Lambda que se invoca al cambiar el email.
 * @property onPassChanged Lambda que se invoca al cambiar la contraseña.
 * @property onRememberChanged Lambda que se invoca al cambiar el estado de "recordarme".
 * @author Pelkidev
 * @version 1.0.0
 */
data class LoginUiCallbacks(
    val onEmailChanged: (ModelStateOutFieldText) -> Unit,
    val onPassChanged: (ModelStateOutFieldText) -> Unit,
    val onRememberChanged: (Boolean) -> Unit
)
