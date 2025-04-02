package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextEmail
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextPassword
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentCheckBoxAndText
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeader
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentTextMix
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.ImageLogo
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import com.mx.liftechnology.registroeducativo.main.util.navigation.LoginRoutes
import org.koin.androidx.compose.koinViewModel


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController() // Crea un controlador de navegación simulado
    LoginScreen(navController) {}
}

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = koinViewModel(),
    onSuccess: () -> Unit,
) {
    val uiState by loginViewModel.uiState.collectAsState()

    // Detectar el cambio de estado y notificar a la Activity
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        var passwordVisible by remember { mutableStateOf(false) }

        /** Header - incluye logo y textos */
        CustomSpace(dimensionResource(id = R.dimen.margin_top_image))

        ImageLogo()

        ComponentHeader(
            stringResource(id = R.string.log_welcome),
            stringResource(id = R.string.log_insert)
        )


        /** Body - campos de captura, recuerdame, y olvidar contraseña*/
        BoxEditTextEmail(
            value = uiState.email,
            enable = true,
            label = stringResource(id = R.string.form_generic_email),
            error = uiState.isErrorEmail
        ) {
            loginViewModel.onEmailChanged(it)
        }

        CustomSpace(dimensionResource(id = R.dimen.margin_between))

        BoxEditTextPassword(
            value = uiState.password,
            statePass = passwordVisible,
            enable = true,
            label = stringResource(id = R.string.form_generic_password),
            error = uiState.isErrorPass,
            onBoxChanged = {
                loginViewModel.onPassChanged(it)
            },
            onStatePassChanged = {
                passwordVisible = it
            }
        )

        CustomSpace(dimensionResource(id = R.dimen.margin_outer))

        ComponentCheckBoxAndText(
            checkBox = uiState.isRemember,
            checkBoxClick = { loginViewModel.onRememberChanged(it) },
            textClick = { navController.navigate(LoginRoutes.FORGET_PASSWORD.route) }
        )

        /** Action - Boton de  salir por servicio  */
        CustomSpace(dimensionResource(id = R.dimen.margin_outer))

        ButtonAction(color_action, stringResource(id = R.string.log_logIn)) {
            loginViewModel.validateFieldsCompose()
        }

        Spacer(modifier = Modifier.weight(1f))

        ComponentTextMix(
            stringResource(id = R.string.log_notAccount),
            stringResource(id = R.string.log_register)
        ) {
            navController.navigate(LoginRoutes.REGISTER_USER.route)
        }

    }
    LoadingAnimation(uiState.isLoading)
}