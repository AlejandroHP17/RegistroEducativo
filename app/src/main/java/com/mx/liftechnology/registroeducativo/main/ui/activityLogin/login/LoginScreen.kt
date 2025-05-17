package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.login.ModelLoginUiState
import com.mx.liftechnology.registroeducativo.main.ui.SharedViewModel
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
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import com.mx.liftechnology.registroeducativo.main.util.navigation.LoginRoutes
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel,
    onSuccess: () -> Unit,
) {
    val uiState by loginViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.uiState) {
        if (uiState.uiState == ModelStateUIEnum.SUCCESS) onSuccess()
    }

    LaunchedEffect (uiState.controlToast) {
        if (uiState.controlToast.showToast) sharedViewModel.modifyShowToast( uiState.controlToast)
        loginViewModel.modifyShowToast(false)
    }

    Column(
        modifier = ModifierOrientation()
    ) {

        HeaderLoginScreen()

        BodyLoginScreen(
            uiState = uiState,
            onEmailChanged = { loginViewModel.onEmailChanged(it) },
            onPassChanged = { loginViewModel.onPassChanged(it) },
            onRememberChanged = { loginViewModel.onRememberChanged(it) },
            navigate = { navController.navigate(LoginRoutes.FORGET_PASSWORD.route) }
        )

        ActionLoginScreen { loginViewModel.validateFieldsCompose() }

        Spacer(modifier = Modifier.weight(1f))

        FooterLoginScreen { navController.navigate(LoginRoutes.REGISTER_USER.route) }
    }

    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

@Composable
fun HeaderLoginScreen() {
    CustomSpace(dimensionResource(id = R.dimen.margin_top_image))

    ImageLogo()

    ComponentHeader(
        title = stringResource(id = R.string.log_welcome),
        body = stringResource(id = R.string.log_insert)
    )
}

@Composable
fun BodyLoginScreen(
    uiState: ModelLoginUiState,
    onEmailChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit,
    onRememberChanged: (Boolean) -> Unit,
    navigate: () -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    BoxEditTextEmail(
        value = uiState.email,
        enable = true,
        label = stringResource(id = R.string.form_generic_email),
    ) { onEmailChanged(it) }

    CustomSpace(dimensionResource(id = R.dimen.margin_between))

    BoxEditTextPassword(
        value = uiState.password,
        statePass = passwordVisible,
        enable = true,
        label = stringResource(id = R.string.form_generic_password),
        onBoxChanged = { onPassChanged(it) },
        onStatePassChanged = { passwordVisible = it }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_outer))

    ComponentCheckBoxAndText(
        checkBox = uiState.isRemember,
        checkBoxClick = { onRememberChanged(it) },
        textClick = { navigate() }
    )
}

@Composable
fun ActionLoginScreen(
    validateFieldsCompose: () -> Unit,
) {
    CustomSpace(dimensionResource(id = R.dimen.margin_outer))

    ButtonAction(
        containerColor = color_action,
        text = stringResource(id = R.string.log_logIn),
        onActionClick = { validateFieldsCompose() }
    )
}

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