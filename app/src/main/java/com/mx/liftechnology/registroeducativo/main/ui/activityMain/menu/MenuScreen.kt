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
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelMenuUIState
import com.mx.liftechnology.registroeducativo.main.ui.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.MenuScreenObject.CONTROL
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.MenuScreenObject.REGISTER
import com.mx.liftechnology.registroeducativo.main.ui.components.AlertDialogMenu
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderMenu
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.MyGridScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.TextSubHeader
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel

/** Menu screen, show the principal view and control the flows
 * @author Alejandro Hernandez Pelcastre
 * @since 1.0.0
 */
@Composable
fun MenuScreen(
    navController: NavHostController,
    menuViewModel: MenuViewModel = koinViewModel(),
    onCloseSession: () ->Unit
) {

    /* Variables locales y en viewmodel */
    val sharedViewModel: SharedViewModel = koinViewModel()
    val uiState by menuViewModel.uiState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val isGroup = remember { mutableStateOf(false) }

    /* Llamadas a servicios cuando se monta la pantalla */
    LaunchedEffect(Unit) {
        if (uiState.studentGroupItem.itemPartial == null){
            menuViewModel.getGroup() //Trae la infomación del listado de grupos correspondientes al profesor
            menuViewModel.getControlMenu() //Pinta la sección de area de control, no depende de nada
        }
    }

    LaunchedEffect ( Unit){
        if(uiState.uiState == ModelStateUIEnum.UNAUTHORIZED){
            sharedViewModel.modifyShowToast(uiState.controlToast)
            menuViewModel.modifyShowToast(false)
            onCloseSession()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        HeaderMenuScreen(
            controlState = uiState,
            onShowDialog = {
                isGroup.value = true
                showDialog.value = true
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            itemsIndexed(listOf(REGISTER, CONTROL)) { _, section ->
                when (section) {

                    REGISTER -> {
                        if (uiState.showControl) {
                            RegisterAreaMenuScreen(
                                controlState = uiState,
                                navController = navController,
                                test = { }
                            )
                        }
                    }

                    CONTROL -> {
                        ControlAreaMenuScreen(
                            controlState = uiState,
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)

    if (showDialog.value) {
        AlertDialogMenu(
            controlState = uiState,
            itemSelectedReturn = {
                menuViewModel.updateGroup(it)
                isGroup.value = false
            },
            itemSelectedPartialReturn = {
                menuViewModel.updatePartial(it)
                isGroup.value = true
            },
            selectType = isGroup.value,
            dismiss = { showDialog.value = false }
        )
    }
}

object MenuScreenObject {
    const val ADAPTER_CONTROL = "Área de Control"
    const val ADAPTER_CONTROL_REGISTER = "Área de Registro y evaluación"
    const val REGISTER = "register"
    const val CONTROL = "control"
}

@Composable
private fun HeaderMenuScreen(controlState: ModelMenuUIState, onShowDialog: (Boolean) -> Unit) {
    ComponentHeaderMenu(
        title = stringResource(R.string.menu_grettins, "Profesor"),
        body = controlState.studentGroupItem.nameItem ?: stringResource(R.string.menu_empty),
        partial = controlState.studentGroupItem.namePartial ?: stringResource(R.string.menu_partial),
        onClick = { onShowDialog(true) }
    )
    CustomSpace(dimensionResource(id = R.dimen.margin_between))
}

@Composable
private fun RegisterAreaMenuScreen(
    controlState: ModelMenuUIState,
    navController: NavHostController,
    test: () -> Unit,
) {
    val menuItemsRegister =
        stringArrayResource(com.mx.liftechnology.data.R.array.menu_items_register)
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextSubHeader(MenuScreenObject.ADAPTER_CONTROL_REGISTER)
        MyGridScreen(controlState.evaluationItems, 628.dp) { selectedItem ->
            when (selectedItem.id) {
                menuItemsRegister[0] -> navController.navigate(MainRoutes.ListStudent.route)
                menuItemsRegister[1] -> navController.navigate(MainRoutes.ListSubject.route)
                menuItemsRegister[2] -> navController.navigate(MainRoutes.RegisterPartial.route)
                menuItemsRegister[3] -> {
                    test()
                }

                menuItemsRegister[4] -> {test()}
            }
        }
    }
    CustomSpace(dimensionResource(id = R.dimen.margin_divided))
}

@Composable
private fun ControlAreaMenuScreen(
    controlState: ModelMenuUIState,
    navController: NavHostController,
) {
    val menuItemsControl = stringArrayResource(com.mx.liftechnology.data.R.array.menu_items_control)
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextSubHeader(MenuScreenObject.ADAPTER_CONTROL)
        MyGridScreen(controlState.controlItems, 200.dp) { selectedItem ->
            when (selectedItem.id) {
                menuItemsControl[0] -> navController.navigate(MainRoutes.RegisterSchool.route)
                menuItemsControl[1] -> navController.navigate(MainRoutes.Profile.route)
            }
        }
    }
    CustomSpace(dimensionResource(id = R.dimen.margin_between))
}