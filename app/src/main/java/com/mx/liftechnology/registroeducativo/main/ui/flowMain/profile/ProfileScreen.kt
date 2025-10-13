package com.mx.liftechnology.registroeducativo.main.ui.flowMain.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import org.koin.androidx.compose.koinViewModel

/**
 * The Profile screen.
 *
 * @param navController The navigation controller.
 * @param profileViewModel The ViewModel for this screen.
 * @param sharedViewModel The shared ViewModel.
 * @param onCloseSession A lambda to be invoked when the session is closed.
 */
@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel,
    onCloseSession : () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        logs("Screen profile")
        HeaderProfile(navController = navController)


        Spacer(modifier = Modifier.weight(1f))

        ActionProfile{
            val control = ModelStateToastUI(
                messageToast = R.string.toast_informative_close_session,
                showToast = true,
                typeToast = ModelStateTypeToastUI.INFORMATIVE
            )
            sharedViewModel.modifyShowToast(control)
            profileViewModel.closeSession()
            onCloseSession()
        }
    }
}

/**
 * The header of the Profile screen.
 *
 * @param navController The navigation controller.
 */
@Composable
fun HeaderProfile(navController: NavHostController){
    ComponentHeaderBack(
        title = stringResource(R.string.profile_name),
        body = stringResource(R.string.tools_empty),
    ) { navController.popBackStack() }
}

/**
 * The action button of the Profile screen.
 *
 * @param closeSessionCompose A lambda to be invoked when the action button is clicked.
 */
@Composable
private fun ActionProfile(
    closeSessionCompose: () -> Unit,
) {
    ButtonAction(
        containerColor = colorAction,
        text = stringResource(R.string.profile_button),
        onActionClick = { closeSessionCompose() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}