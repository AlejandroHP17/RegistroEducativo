package com.mx.liftechnology.registroeducativo.main.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.TypeToastUi
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import org.koin.androidx.compose.koinViewModel

/**
 * Pantalla de perfil de usuario.
 * 
 * Muestra la información del perfil del usuario y permite cerrar sesión.
 *
 * @param navController El controlador de navegación para gestionar los desplazamientos.
 * @param profileViewModel El ViewModel para esta pantalla.
 * @param sharedViewModel El ViewModel compartido para la comunicación entre pantallas (ej: mostrar toasts).
 * @param onCloseSession Lambda que se invoca cuando se cierra la sesión.
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
        HeaderProfile(navController = navController)


        Spacer(modifier = Modifier.weight(1f))

        ActionProfile{
            val control = ToastUiState(
                messageToast = R.string.toast_informative_close_session,
                showToast = true,
                typeToast = TypeToastUi.INFORMATIVE
            )
            sharedViewModel.modifyShowToast(control)
            profileViewModel.closeSession()
            onCloseSession()
        }
    }
}

/**
 * Encabezado de la pantalla de perfil.
 *
 * @param navController El controlador de navegación.
 */
@Composable
fun HeaderProfile(navController: NavHostController){
    ComponentHeaderBack(
        title = stringResource(R.string.profile_name),
        body = stringResource(R.string.tools_empty),
    ) { navController.popBackStack() }
}

/**
 * Botón de acción de la pantalla de perfil.
 *
 * @param closeSessionCompose Lambda que se invoca cuando se hace clic en el botón de acción.
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