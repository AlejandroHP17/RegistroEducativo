package com.mx.liftechnology.registroeducativo.main.ui.activityMain.profile

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
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = koinViewModel(),
    onSuccess : () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {

        HeaderProfile(navController = navController)


        Spacer(modifier = Modifier.weight(1f))

        ActionProfile{
            profileViewModel.closeSession()
            onSuccess()
        }
    }
}

@Composable
fun HeaderProfile(navController: NavHostController){
    ComponentHeaderBack(
        title = stringResource(R.string.profile_name),
        body = stringResource(R.string.tools_empty),
    ) { navController.popBackStack() }
}

@Composable
private fun ActionProfile(
    closeSessionCompose: () -> Unit,
) {
    ButtonAction(
        containerColor = color_action,
        text = stringResource(R.string.profile_button),
        onActionClick = { closeSessionCompose() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}