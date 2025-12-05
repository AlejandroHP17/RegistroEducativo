package com.mx.liftechnology.registroeducativo.main.ui.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.event.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.auth.LoginUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.auth.LoginUiInputs
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldEmail
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldPassword
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ComponentCheckBoxAndText
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ComponentHeader
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ComponentTextMix
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ImageLogo
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ModifierOrientation
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import com.mx.liftechnology.registroeducativo.main.util.navigation.AppRoutes
import org.koin.androidx.compose.koinViewModel

/**
 * Pantalla de inicio de sesión.
 *
 * @param navController El controlador de navegación.
 * @param loginViewModel El ViewModel para esta pantalla.
 * @param sharedViewModel El ViewModel compartido.
 * @param onSuccess Lambda que se invoca cuando el inicio de sesión es exitoso.
 */
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel,
    onSuccess: () -> Unit,
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val inputState by loginViewModel.inputState.collectAsStateWithLifecycle()

    // Consumir eventos de navegación en lugar de observar estados
    LaunchedEffect(Unit) {
        loginViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateToHome -> onSuccess()
                else -> { /* Otros eventos se manejan en otros lugares */ }
            }
        }
    }

    // Observa el estado del toast del ViewModel local y lo propaga al SharedViewModel
    // para que se muestre por encima de toda la navegación
    LaunchedEffect(uiState.controlToast) {
        if (uiState.controlToast.showToast) {
            sharedViewModel.modifyShowToast(uiState.controlToast)
            loginViewModel.modifyShowToast(false)
        }
    }

    Column(
        modifier = ModifierOrientation()
    ) {
        HeaderLoginScreen()

        BodyLoginScreen(
            inputState = inputState,
            callbacks = LoginUiCallbacks(
                onEmailChanged = { loginViewModel.onEmailChanged(it) },
                onPassChanged = { loginViewModel.onPassChanged(it) },
                onRememberChanged = { loginViewModel.onRememberChanged(it) }
            ),
            navigate = { navController.navigate(AppRoutes.Auth.FORGET_PASSWORD) }
        )

        ActionLoginScreen { loginViewModel.validateFieldsCompose() }

        Spacer(modifier = Modifier.weight(1f))

        FooterLoginScreen { navController.navigate(AppRoutes.Auth.REGISTER_USER) }
    }

    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

/**
 * Encabezado de la pantalla de inicio de sesión.
 */
@Composable
fun HeaderLoginScreen() {
    CustomSpace(dimensionResource(id = R.dimen.margin_top_image))

    ImageLogo()

    ComponentHeader(
        title = stringResource(id = R.string.log_welcome),
        body = stringResource(id = R.string.log_insert)
    )
}

/**
 * Cuerpo de la pantalla de inicio de sesión.
 *
 * @param inputState El estado de los campos de entrada.
 * @param callbacks Los callbacks para los campos de entrada.
 * @param navigate Lambda que se invoca para navegar a la pantalla de "Olvidé mi contraseña".
 */
@Composable
fun BodyLoginScreen(
    inputState: LoginUiInputs,
    callbacks: LoginUiCallbacks,
    navigate: () -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextFieldEmail(
        modelText = inputState.emailInputState,
        enableBox = true,
        label = stringResource(id = R.string.form_generic_email),
    ) { callbacks.onEmailChanged(it) }

    CustomSpace(dimensionResource(id = R.dimen.margin_between))

    TextFieldPassword(
        modelText = inputState.passInputState,
        statePass = passwordVisible,
        enable = true,
        label = stringResource(id = R.string.form_generic_password),
        onBoxChanged = { callbacks.onPassChanged(it) },
        onStatePassChanged = { passwordVisible = it }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_outer))

    ComponentCheckBoxAndText(
        checkBox = inputState.isRemember,
        checkBoxClick = { callbacks.onRememberChanged(it) },
        textClick = { navigate() }
    )
}

/**
 * Botón de acción de la pantalla de inicio de sesión.
 *
 * @param validateFieldsCompose Lambda que se invoca cuando se hace clic en el botón de acción.
 */
@Composable
fun ActionLoginScreen(
    validateFieldsCompose: () -> Unit,
) {
    CustomSpace(dimensionResource(id = R.dimen.margin_outer))

    ButtonAction(
        containerColor = colorAction,
        text = stringResource(id = R.string.log_logIn),
        onActionClick = { validateFieldsCompose() }
    )
}

/**
 * The footer of the Login screen.
 *
 * @param navigate A lambda to be invoked to navigate to the "Register User" screen.
 */
@Composable
fun FooterLoginScreen(
    navigate: () -> Unit,
) {
    CustomSpace(dimensionResource(id = R.dimen.margin_divided))
    ComponentTextMix(
        text = stringResource(id = R.string.log_notAccount),
        textClick = stringResource(id = R.string.log_register),
        onTextClick = { navigate() }
    )
}


@Preview(showBackground = true)
@Composable()
private fun LoginPreview(){
    Column(
        modifier = ModifierOrientation()
    ) {
        HeaderLoginScreen()

        BodyLoginScreen(
            inputState = LoginUiInputs(),
            callbacks = LoginUiCallbacks(
                onEmailChanged = { },
                onPassChanged = {  },
                onRememberChanged = {  }
            ),
            navigate = { }
        )

        ActionLoginScreen {  }

        Spacer(modifier = Modifier.weight(1f))

        FooterLoginScreen { }
    }
}