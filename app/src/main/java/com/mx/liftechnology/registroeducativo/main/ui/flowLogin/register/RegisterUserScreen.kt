package com.mx.liftechnology.registroeducativo.main.ui.flowLogin.register

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.ModelRegisterUserCallbacksUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.ModelRegisterUserInputsUI
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextEmail
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextPassword
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextSimpleGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.ModifierOrientation
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterUserScreen(
    navController: NavHostController,
    registerUserViewModel: RegisterUserViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel,
) {
    val uiState by registerUserViewModel.uiState.collectAsStateWithLifecycle()
    val inputState by registerUserViewModel.inputState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(uiState.uiState) {
        if (uiState.uiState == ModelStateUIEnum.SUCCESS) navController.popBackStack()
    }

    LaunchedEffect(uiState.controlToast) {
        if (uiState.controlToast.showToast) sharedViewModel.modifyShowToast(uiState.controlToast)
        registerUserViewModel.modifyShowToast(false)
    }

    Column(
        ModifierOrientation()
    ) {
        HeaderRegisterUserScreen { navController.popBackStack() }

        BodyRegisterUserScreen(
            inputState = inputState,
            callbacks = ModelRegisterUserCallbacksUI(
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
    inputState: ModelRegisterUserInputsUI,
    callbacks: ModelRegisterUserCallbacksUI,
    getRules: String,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var repeatPasswordVisible by remember { mutableStateOf(false) }

    BoxEditTextEmail(
        value = inputState.emailInputState,
        enable = true,
        label = stringResource(id = R.string.form_generic_email),
        onBoxChanged = { callbacks.onEmailChanged(it) }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_between))

    BoxEditTextPassword(
        value = inputState.passInputState,
        statePass = passwordVisible,
        enable = true,
        label = stringResource(id = R.string.form_generic_password),
        onBoxChanged = { callbacks.onPassChanged(it) },
        onStatePassChanged = {
            passwordVisible = it
        }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_between))

    BoxEditTextPassword(
        value = inputState.repeatPassInputState,
        statePass = repeatPasswordVisible,
        enable = true,
        label = stringResource(id = R.string.form_reg_repeat_password),
        onBoxChanged = { callbacks.onRepeatPassChanged(it) },
        onStatePassChanged = { repeatPasswordVisible = it }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_outer))

    TextBody(getRules)

    CustomSpace(dimensionResource(id = R.dimen.margin_divided))

    BoxEditTextSimpleGeneric(
        value = inputState.codeInputState,
        enable = true,
        label = stringResource(id = R.string.form_reg_code),
        onBoxChanged = { callbacks.onCodeChanged(it) }
    )
}


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