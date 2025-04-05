package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.login.RegisterUserUiState
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextEmail
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextPassword
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.ModifierOrientation
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterUserScreen(
    navController: NavHostController,
    registerUserViewModel: RegisterViewModel = koinViewModel(),
) {
    val uiState by registerUserViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Detectar el cambio de estado y notificar a la Activity
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.popBackStack()
        }
    }
    Column(
        ModifierOrientation()
    ) {

        HeaderRegisterUserScreen { navController.popBackStack() }

        BodyRegisterUserScreen(
            uiState = uiState,
            onEmailChanged = { registerUserViewModel.onEmailChanged(it) },
            onPassChanged = { registerUserViewModel.onPassChanged(it) },
            onRepeatPassChanged = { registerUserViewModel.onRepeatPassChanged(it) },
            getRules = registerUserViewModel.getRules(context),
            onCodeChanged = { registerUserViewModel.onCodeChanged(it) }
        )
        Spacer(modifier = Modifier.weight(1f))
        FooterRegisterUserScreen { registerUserViewModel.validateFieldsCompose() }
    }
    LoadingAnimation(uiState.isLoading)
}


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

@Composable
fun BodyRegisterUserScreen(
    uiState: RegisterUserUiState,
    onEmailChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit,
    onRepeatPassChanged: (String) -> Unit,
    getRules: String,
    onCodeChanged: (String) -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var repeatPasswordVisible by remember { mutableStateOf(false) }

    BoxEditTextEmail(
        value = uiState.email,
        enable = true,
        label = stringResource(id = R.string.form_generic_email),
        onBoxChanged = { onEmailChanged(it) }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_between))

    BoxEditTextPassword(
        value = uiState.password,
        statePass = passwordVisible,
        enable = true,
        label = stringResource(id = R.string.form_generic_password),
        onBoxChanged = { onPassChanged(it) },
        onStatePassChanged = {
            passwordVisible = it
        }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_between))

    BoxEditTextPassword(
        value = uiState.repeatPassword,
        statePass = repeatPasswordVisible,
        enable = true,
        label = stringResource(id = R.string.form_reg_repeat_password),
        onBoxChanged = { onRepeatPassChanged(it) },
        onStatePassChanged = { repeatPasswordVisible = it }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_outer))

    TextBody(getRules)

    CustomSpace(dimensionResource(id = R.dimen.margin_divided))

    BoxEditTextGeneric(
        value = uiState.code.valueText,
        enable = true,
        label = stringResource(id = R.string.form_reg_code),
        error = uiState.code,
        onBoxChanged = { onCodeChanged(it) }
    )
}


@Composable
fun FooterRegisterUserScreen(
    validateFieldsCompose: () -> Unit,
) {
    ButtonAction(
        containerColor = color_action,
        text = stringResource(id = R.string.log_register),
        onActionClick = { validateFieldsCompose() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}