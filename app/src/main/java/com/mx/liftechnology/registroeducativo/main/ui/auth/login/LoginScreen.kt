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
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.ModelLoginCallbacksUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.ModelLoginInputsUI
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextEmail
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextPassword
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentCheckBoxAndText
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeader
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentTextMix
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.ImageLogo
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.ModifierOrientation
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import com.mx.liftechnology.registroeducativo.main.util.navigation.LoginRoutes
import org.koin.androidx.compose.koinViewModel

/**
 * The Login screen.
 *
 * @param navController The navigation controller.
 * @param loginViewModel The ViewModel for this screen.
 * @param sharedViewModel The shared ViewModel.
 * @param onSuccess A lambda to be invoked when the login is successful.
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

    LaunchedEffect(uiState.uiState) {
        if (uiState.uiState == ModelStateUIEnum.SUCCESS) onSuccess()
    }

    // Observa el estado del toast del ViewModel local y lo propaga al SharedViewModel
    // para que se muestre por encima de toda la navegación
    LaunchedEffect(uiState.controlToast) {
        if (uiState.controlToast.showToast) {
            sharedViewModel.modifyShowToast(uiState.controlToast)
            // Oculta el toast en el ViewModel local después de propagarlo
            loginViewModel.modifyShowToast(false)
        }
    }

    Column(
        modifier = ModifierOrientation()
    ) {
        HeaderLoginScreen()

        BodyLoginScreen(
            inputState = inputState,
            callbacks = ModelLoginCallbacksUI(
                onEmailChanged = { loginViewModel.onEmailChanged(it) },
                onPassChanged = { loginViewModel.onPassChanged(it) },
                onRememberChanged = { loginViewModel.onRememberChanged(it) }
            ),
            navigate = { navController.navigate(LoginRoutes.FORGET_PASSWORD.route) }
        )

        ActionLoginScreen { loginViewModel.validateFieldsCompose() }

        Spacer(modifier = Modifier.weight(1f))

        FooterLoginScreen { navController.navigate(LoginRoutes.REGISTER_USER.route) }
    }

    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

/**
 * The header of the Login screen.
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
 * The body of the Login screen.
 *
 * @param inputState The state of the input fields.
 * @param callbacks The callbacks for the input fields.
 * @param navigate A lambda to be invoked to navigate to the "Forget Password" screen.
 */
@Composable
fun BodyLoginScreen(
    inputState: ModelLoginInputsUI,
    callbacks: ModelLoginCallbacksUI,
    navigate: () -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    BoxEditTextEmail(
        value = inputState.emailInputState,
        enableBox = true,
        label = stringResource(id = R.string.form_generic_email),
    ) { callbacks.onEmailChanged(it) }

    CustomSpace(dimensionResource(id = R.dimen.margin_between))

    BoxEditTextPassword(
        value = inputState.passInputState,
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
 * The action button of the Login screen.
 *
 * @param validateFieldsCompose A lambda to be invoked when the action button is clicked.
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
fun LoginPreview(){
    Column(
        modifier = ModifierOrientation()
    ) {
        HeaderLoginScreen()

        BodyLoginScreen(
            inputState = ModelLoginInputsUI(),
            callbacks = ModelLoginCallbacksUI(
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