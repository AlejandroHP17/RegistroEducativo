package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.forgetPassword

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextEmail
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
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
            /** Header - incluye logo y textos */
            ComponentHeaderBack(
                stringResource(id = R.string.forget_pass_welcome),
                stringResource(id = R.string.forget_pass_insert)
            ){
                navController.popBackStack()
            }

            /** Body - campos de captura, recuerdame, y olvidar contraseña*/

            BoxEditTextEmail(
                value = uiState.email,
                enable = true,
                label = stringResource(id = R.string.form_generic_email),
                error = uiState.isErrorEmail
            ) {
                forgetPasswordViewModel.onEmailChanged(it)
            }

            CustomSpace(dimensionResource(id = R.dimen.margin_outer))

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