package com.mx.liftechnology.registroeducativo.main.ui.auth.forgetPassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldEmail
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ModifierOrientation
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import org.koin.androidx.compose.koinViewModel

/**
 * The "Forget Password" screen.
 *
 * @param navController The navigation controller.
 * @param forgetPasswordViewModel The ViewModel for this screen.
 */
@Composable
fun ForgetPasswordScreen(
    navController: NavHostController,
    forgetPasswordViewModel: ForgetPasswordViewModel = koinViewModel(),
) {
    val uiState by forgetPasswordViewModel.uiState.collectAsStateWithLifecycle()
    val emailState by forgetPasswordViewModel.emailState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        ModifierOrientation()
    ) {
        HeaderForgetPasswordScreen { navController.popBackStack() }

        BodyForgetPasswordScreen(
            emailState = emailState,
            onEmailChanged = { forgetPasswordViewModel.onEmailChanged(it) },
            getRules = forgetPasswordViewModel.getRules(context)
        )
        Spacer(modifier = Modifier.weight(1f))
        FooterForgetPasswordScreen { forgetPasswordViewModel.validateFieldsCompose() }
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

/**
 * The header of the "Forget Password" screen.
 *
 * @param navigate A lambda to be invoked when the back button is clicked.
 */
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

/**
 * The body of the "Forget Password" screen.
 *
 * @param emailState The state of the email input field.
 * @param onEmailChanged A lambda to be invoked when the email input changes.
 * @param getRules The rules for forgetting a password.
 */
@Composable
fun BodyForgetPasswordScreen(
    emailState : ModelStateOutFieldText,
    onEmailChanged: (ModelStateOutFieldText) -> Unit,
    getRules: String,
) {
    TextFieldEmail(
        modelText = emailState,
        enableBox = true,
        label = stringResource(id = R.string.form_generic_email),
        onBoxChanged = { onEmailChanged(it) }
    )

    CustomSpace(dimensionResource(id = R.dimen.margin_outer))

    TextBody(getRules)
}

/**
 * The footer of the "Forget Password" screen.
 *
 * @param validateFieldsCompose A lambda to be invoked when the action button is clicked.
 */
@Composable
fun FooterForgetPasswordScreen(
    validateFieldsCompose: () -> Unit,
) {
    ButtonAction(
        containerColor = colorAction,
        text = stringResource(id = R.string.forget_pass_button),
        onActionClick = { validateFieldsCompose() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}

@Preview(showBackground = true)
@Composable()
private fun ForgetPasswordPreview(){
    Column(
        ModifierOrientation()
    ) {
        HeaderForgetPasswordScreen { }

        BodyForgetPasswordScreen(
            emailState = ModelStateOutFieldText(),
            onEmailChanged = {  },
            getRules = "forgetPasswordViewModel.getRules(context)"
        )
        Spacer(modifier = Modifier.weight(1f))
        FooterForgetPasswordScreen { }
    }
}