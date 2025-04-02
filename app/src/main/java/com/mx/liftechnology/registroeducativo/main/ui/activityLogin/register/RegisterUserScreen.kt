package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextEmail
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextPassword
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
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
            //ShowCustomToast()
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        var passwordVisible by remember { mutableStateOf(false) }
        var repeatPasswordVisible by remember { mutableStateOf(false) }


        /** Header - incluye logo y textos */
        ComponentHeaderBack(
            stringResource(id = R.string.reg_welcome),
            stringResource(id = R.string.reg_insert)
        ) {
            navController.popBackStack()
        }


        /** Body - campos de captura, recuerdame, y olvidar contraseña*/
        BoxEditTextEmail(
            value = uiState.email,
            enable = true,
            label = stringResource(id = R.string.form_generic_email),
            error = uiState.isErrorEmail
        ) {
            registerUserViewModel.onEmailChanged(it)
        }

        CustomSpace(dimensionResource(id = R.dimen.margin_between))

        BoxEditTextPassword(
            value = uiState.password,
            statePass = passwordVisible,
            enable = true,
            label = stringResource(id = R.string.form_generic_password),
            error = uiState.isErrorPass,
            onBoxChanged = {
                registerUserViewModel.onPassChanged(it)
            },
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
            error = uiState.isErrorRepeatPass,
            onBoxChanged = {
                registerUserViewModel.onRepeatPassChanged(it)
            },
            onStatePassChanged = {
                repeatPasswordVisible = it
            }
        )

        CustomSpace(dimensionResource(id = R.dimen.margin_outer))

        TextBody(
            registerUserViewModel.getRules(context)
        )

        CustomSpace(dimensionResource(id = R.dimen.margin_divided))

        BoxEditTextGeneric(
            value = uiState.code,
            enable = true,
            label = stringResource(id = R.string.form_reg_code),
            error = uiState.isErrorCode
        ) {
            registerUserViewModel.onCodeChanged(it)
        }

        Spacer(modifier = Modifier.weight(1f))

        ButtonAction(color_action, stringResource(id = R.string.log_register)) {
            registerUserViewModel.validateFieldsCompose()
        }
    }
    LoadingAnimation(uiState.isLoading)
}