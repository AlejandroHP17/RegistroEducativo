package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelMenuUIState
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.MenuScreenObject.CONTROL
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.MenuScreenObject.HEADER
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.MenuScreenObject.REGISTER
import com.mx.liftechnology.registroeducativo.main.ui.components.AlertDialogMenu
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderMenu
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.MyGridScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.TextSubHeader
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel


@Composable
fun MenuScreen(
    navController: NavHostController,
    menuViewModel: MenuViewModel = koinViewModel(),
) {
    // Llamadas a servicios cuando se monta la pantalla
    LaunchedEffect(Unit) {
        menuViewModel.getGroup()
        menuViewModel.getControlMenu()
    }

    val uiState by menuViewModel.uiState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    )
    {
        itemsIndexed(listOf(HEADER, REGISTER, CONTROL)) { _, section ->
            when (section) {
                HEADER -> {
                    HeaderMenuScreen(
                        uiState = uiState,
                        onShowDialog = { showDialog.value = true }
                    )
                }

                REGISTER -> {
                    if (uiState.showControl) {
                        RegisterAreaMenuScreen(
                            uiState = uiState,
                            navController = navController
                        )
                    }
                }

                CONTROL -> {
                    ControlAreaMenuScreen(
                        uiState = uiState,
                        navController = navController
                    )
                }
            }
        }
    }

    LoadingAnimation(uiState.isLoading)


    if (showDialog.value) {
        AlertDialogMenu(
            uiState = uiState,
            itemSelectedReturn = { menuViewModel.updateGroup(it) },
            dismiss = { showDialog.value = false }
        )
    }
}

object MenuScreenObject {
    const val ADAPTER_CONTROL = "Área de Control"
    const val ADAPTER_CONTROL_REGISTER = "Área de Registro y evaluación"
    const val HEADER = "header"
    const val REGISTER = "register"
    const val CONTROL = "control"
}

@Composable
private fun HeaderMenuScreen(uiState: ModelMenuUIState, onShowDialog: (Boolean) -> Unit) {
    ComponentHeaderMenu(
        title = stringResource(R.string.menu_grettins, "Profesor"),
        body = uiState.studentGroupItem.nameItem ?: stringResource(R.string.menu_empty)
    ) {
        onShowDialog(true)
    }
    CustomSpace(dimensionResource(id = R.dimen.margin_between))
}

@Composable
private fun RegisterAreaMenuScreen(uiState: ModelMenuUIState, navController: NavHostController) {
    val menuItemsRegister =
        stringArrayResource(com.mx.liftechnology.data.R.array.menu_items_register)
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextSubHeader(MenuScreenObject.ADAPTER_CONTROL_REGISTER)
        MyGridScreen(uiState.evaluationItems, 628.dp) { selectedItem ->

            when (selectedItem.id) {
                menuItemsRegister[0] -> navController.navigate(MainRoutes.ListStudent.route)
                menuItemsRegister[1] -> navController.navigate(MainRoutes.ListSubject.route)
                menuItemsRegister[2] -> navController.navigate(MainRoutes.RegisterPartial.route)
                menuItemsRegister[3] -> {}
                menuItemsRegister[4] -> {}
            }
        }
    }
    CustomSpace(dimensionResource(id = R.dimen.margin_divided))
}

@Composable
private fun ControlAreaMenuScreen(uiState: ModelMenuUIState, navController: NavHostController) {
    val menuItemsControl = stringArrayResource(com.mx.liftechnology.data.R.array.menu_items_control)
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextSubHeader(MenuScreenObject.ADAPTER_CONTROL)
        MyGridScreen(uiState.controlItems, 200.dp) { selectedItem ->
            when (selectedItem.id) {
                menuItemsControl[0] -> navController.navigate(MainRoutes.RegisterSchool.route)
                menuItemsControl[1] -> navController.navigate(MainRoutes.Profile.route)
            }
        }
    }
    CustomSpace(dimensionResource(id = R.dimen.margin_between))
}