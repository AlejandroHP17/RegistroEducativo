package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.forgetPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_transparent
import org.koin.androidx.compose.koinViewModel


@Composable
fun ForgetPasswordScreen(navController: NavHostController, forgetPasswordViewModel: ForgetPasswordViewModel = koinViewModel()) {
    val uiState by forgetPasswordViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.margin_outer))
        )
        {
            var email by remember { mutableStateOf("") }

            /** Header - incluye logo y textos */
            ComponentHeaderBack(
                stringResource(id = R.string.forget_pass_welcome),
                stringResource(id = R.string.forget_pass_insert)
            ){
                navController.popBackStack()
            }

            /** Body - campos de captura, recuerdame, y olvidar contraseña*/

            BoxEditTextEmail(
                value = email,
                read = false,
                label = stringResource(id = R.string.form_generic_email),
                error = uiState.isErrorEmail
            ) {
                email = it
                forgetPasswordViewModel.onEmailChanged(email)
            }

            Spacer(modifier = Modifier
                .height(dimensionResource(id = R.dimen.margin_outer))
                .background(color_transparent))

            TextBody(
                forgetPasswordViewModel.getRules(context)
            )

            /** Action - Boton de  salir por servicio  */

            Spacer(modifier = Modifier.weight(1f))

            ButtonAction(color_action, stringResource(id = R.string.forget_pass_button)){
               forgetPasswordViewModel.validateFieldsCompose()
            }
        }
        LoadingAnimation(uiState.isLoading)
    }

}