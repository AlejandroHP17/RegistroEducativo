package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.forgetPassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.login.ModelLoginUiState
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextEmail
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.ModifierOrientation
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import org.koin.androidx.compose.koinViewModel


@Composable
fun ForgetPasswordScreen(
    navController: NavHostController,
    forgetPasswordViewModel: ForgetPasswordViewModel = koinViewModel(),
) {
    val uiState by forgetPasswordViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        ModifierOrientation()
    ) {
        HeaderForgetPasswordScreen { navController.popBackStack() }

        BodyForgetPasswordScreen(
            uiState = uiState,
            onEmailChanged = { forgetPasswordViewModel.onEmailChanged(it) },
            getRules = forgetPasswordViewModel.getRules(context)
        )
        Spacer(modifier = Modifier.weight(1f))
        FooterForgetPasswordScreen { forgetPasswordViewModel.validateFieldsCompose() }
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

@Composable
fun HeaderForgetPasswordScreen(
    navigate: () -> Unit,
) {
    ComponentHeaderBack(
        title = stringResource(id = R.string.forget_pass_welcome),
        body = stringResource(id = R.string.forget_pass_insert),
        onReturnClick = { navigate() }
    )
}

@Composable
fun BodyForgetPasswordScreen(
    uiState: ModelLoginUiState,
    onEmailChanged: (String) -> Unit,
    getRules: String,
) {
    BoxEditTextEmail(
        value = uiState.email,
        enable = true,
        label = stringResource(id = R.string.form_generic_email),
        onBoxChanged = { onEmailChanged(it) }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_outer))

    TextBody(getRules)
}


@Composable
fun FooterForgetPasswordScreen(
    validateFieldsCompose: () -> Unit,
) {
    ButtonAction(
        containerColor = color_action,
        text = stringResource(id = R.string.forget_pass_button),
        onActionClick = { validateFieldsCompose() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}