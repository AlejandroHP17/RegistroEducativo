/**
 * @file Define la pantalla para el registro de un nuevo usuario.
 * @author PelkiDev
 * @version 1.0.0
 */
package com.mx.liftechnology.registroeducativo.main.ui.auth.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.events.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.RegisterUserUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.RegisterUserUiInputs
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldEmail
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldPassword
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ModifierOrientation
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import org.koin.androidx.compose.koinViewModel

/**
 * Pantalla para el registro de un nuevo usuario.
 * Este Composable maneja la UI y la lógica para que un nuevo usuario pueda crear una cuenta.
 *
 * @param navController El controlador de navegación para gestionar los desplazamientos.
 * @param registerUserViewModel El ViewModel específico para esta pantalla.
 * @param sharedViewModel El ViewModel compartido para la comunicación entre pantallas (ej: mostrar toasts).
 * @author PelkiDev
 * @version 1.0.0
 */
@Composable
fun RegisterUserScreen(
    navController: NavHostController,
    registerUserViewModel: RegisterUserViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel,
) {
    val uiState by registerUserViewModel.uiState.collectAsStateWithLifecycle()
    val inputState by registerUserViewModel.inputState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    // Consumir eventos de navegación en lugar de observar estados
    LaunchedEffect(Unit) {
        registerUserViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateBack -> navController.popBackStack()
                else -> { /* Otros eventos se manejan en otros lugares */ }
            }
        }
    }

      // Observa el estado del toast del ViewModel local y lo propaga al SharedViewModel
    // para que se muestre por encima de toda la navegación
    LaunchedEffect(uiState.controlToast) {
        if (uiState.controlToast.showToast) {
            sharedViewModel.modifyShowToast(uiState.controlToast)
            // Oculta el toast en el ViewModel local después de propagarlo
            registerUserViewModel.modifyShowToast(false)
        }
    }

    Column(
        ModifierOrientation()
    ) {
        HeaderRegisterUserScreen { navController.popBackStack() }

        BodyRegisterUserScreen(
            inputState = inputState,
            callbacks = RegisterUserUiCallbacks(
                onEmailChanged = { registerUserViewModel.onEmailChanged(it) },
                onPassChanged = { registerUserViewModel.onPassChanged(it) },
                onRepeatPassChanged = { registerUserViewModel.onRepeatPassChanged(it) },
                onCodeChanged = { registerUserViewModel.onCodeChanged(it) }
            ),
            getRules = registerUserViewModel.getRules(context),
        )
        Spacer(modifier = Modifier.weight(1f))
        FooterRegisterUserScreen { registerUserViewModel.validateFieldsCompose() }
    }

    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

/**
 * Encabezado de la pantalla de Registro de Usuario.
 *
 * @param navigate Lambda que se invoca cuando el botón de retroceso es pulsado.
 * @author PelkiDev
 * @version 1.0.0
 */
@Composable
fun HeaderRegisterUserScreen(
    navigate: () -> Unit,
) {
    ComponentHeaderBack(
        title = stringResource(id = R.string.reg_welcome),
        body = stringResource(id = R.string.reg_insert),
        onReturnClick = { navigate() }
    )
}

/**
 * Cuerpo de la pantalla de Registro de Usuario.
 *
 * @param inputState El estado de los campos de entrada.
 * @param callbacks Los callbacks para los cambios en los campos de entrada.
 * @param getRules El texto que contiene las reglas para la contraseña.
 * @author PelkiDev
 * @version 1.0.0
 */
@Composable
fun BodyRegisterUserScreen(
    inputState: RegisterUserUiInputs,
    callbacks: RegisterUserUiCallbacks,
    getRules: String,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var repeatPasswordVisible by remember { mutableStateOf(false) }

    TextFieldEmail(
        modelText = inputState.emailInputState,
        enableBox = true,
        label = stringResource(id = R.string.form_generic_email),
        onBoxChanged = { callbacks.onEmailChanged(it) }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_between))

    TextFieldPassword(
        modelText = inputState.passInputState,
        statePass = passwordVisible,
        enable = true,
        label = stringResource(id = R.string.form_generic_password),
        onBoxChanged = { callbacks.onPassChanged(it) },
        onStatePassChanged = { passwordVisible = it }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_between))

    TextFieldPassword(
        modelText = inputState.repeatPassInputState,
        statePass = repeatPasswordVisible,
        enable = true,
        label = stringResource(id = R.string.form_reg_repeat_password),
        onBoxChanged = { callbacks.onRepeatPassChanged(it) },
        onStatePassChanged = { repeatPasswordVisible = it }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_outer))

    TextBody(getRules)

    CustomSpace(dimensionResource(id = R.dimen.margin_divided))

    TextFieldGeneric(
        modelText = inputState.codeInputState,
        enable = true,
        label = stringResource(id = R.string.form_reg_code),
        regex = ModelRegex.COMPLETE_TEXT,
        onBoxChanged = { callbacks.onCodeChanged(it) }
    )
}

/**
 * Pie de página de la pantalla de Registro de Usuario.
 *
 * @param validateFieldsCompose Lambda que se invoca para iniciar la validación de los campos.
 * @author PelkiDev
 * @version 1.0.0
 */
@Composable
fun FooterRegisterUserScreen(
    validateFieldsCompose: () -> Unit,
) {
    ButtonAction(
        containerColor = colorAction,
        text = stringResource(id = R.string.log_register),
        onActionClick = { validateFieldsCompose() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}


@Preview(showBackground = true)
@Composable()
private fun RegisterUserPreview(){
    Column(
        ModifierOrientation()
    ) {
        HeaderRegisterUserScreen { }

        BodyRegisterUserScreen(
            inputState = RegisterUserUiInputs(),
            callbacks = RegisterUserUiCallbacks(
                onEmailChanged = {  },
                onPassChanged = {  },
                onRepeatPassChanged = {  },
                onCodeChanged = { }
            ),
            getRules = "registerUserViewModel.getRules(context)",
        )
        Spacer(modifier = Modifier.weight(1f))
        FooterRegisterUserScreen { }
    }
}