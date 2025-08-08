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
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelMenuUIData
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelMenuUIDialog
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.MenuScreenObject.CONTROL
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.MenuScreenObject.REGISTER
import com.mx.liftechnology.registroeducativo.main.ui.components.AlertDialogMenu
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderMenu
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.MyGridScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.TextSubHeader
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel

/** Menu screen, show the principal view and control the flows
 * @author Alejandro Hernandez Pelcastre
 * @since 1.0.0
 */
@Composable
fun MenuScreen(
    reload: Boolean,
    navController: NavHostController,
    menuViewModel: MenuViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel,
    onCloseSession: () ->Unit
) {

    /* Variables locales y en viewmodel */
    val uiState by menuViewModel.uiState.collectAsState()
    val uiDialog by menuViewModel.uiDialog.collectAsState()
    val uiData by menuViewModel.uiData.collectAsState()

    val showDialog = remember { mutableStateOf(false) }
    val isGroup = remember { mutableStateOf(false) }

    LaunchedEffect(reload) {
        if (uiDialog.studentGroupItem.itemPartial == null || reload){
            menuViewModel.getGroup() //Trae la infomación del listado de grupos correspondientes al profesor
            menuViewModel.getControlMenu() //Pinta la sección de area de control, no depende de nada
        }
    }

    LaunchedEffect ( uiState.uiState){
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
        logs("Menu")
        HeaderMenuScreen(
            uiDialog = uiDialog,
            onShowDialog = {
                isGroup.value = true
                showDialog.value = true
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(listOf(REGISTER, CONTROL)) { _, section ->
                when (section) {

                    REGISTER -> {
                        if (uiState.showControl) {
                            RegisterAreaMenuScreen(
                                uiData = uiData,
                                navController = navController,
                                test = { }
                            )
                        }
                    }

                    CONTROL -> {
                        ControlAreaMenuScreen(
                            uiData = uiData,
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
            uiDialog = uiDialog,
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
private fun HeaderMenuScreen(uiDialog: ModelMenuUIDialog, onShowDialog: (Boolean) -> Unit) {
    ComponentHeaderMenu(
        title = stringResource(R.string.menu_grettins, "Profesor"),
        body = uiDialog.studentGroupItem.nameItem ?: stringResource(R.string.menu_empty),
        partial = uiDialog.studentGroupItem.namePartial ?: stringResource(R.string.menu_partial),
        onClick = { onShowDialog(true) }
    )
    CustomSpace(dimensionResource(id = R.dimen.margin_between))
}

@Composable
private fun RegisterAreaMenuScreen(
    uiData: ModelMenuUIData,
    navController: NavHostController,
    test: () -> Unit,
) {
    val menuItemsRegister =
        stringArrayResource(com.mx.liftechnology.data.R.array.menu_items_register)
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        logs("Recompuso vista menu", "click")
        TextSubHeader(MenuScreenObject.ADAPTER_CONTROL_REGISTER)
        MyGridScreen(uiData.evaluationItems, 628.dp) { selectedItem ->
            when (selectedItem.id) {
                menuItemsRegister[0] -> {
                    logs("go to student", "click")
                    navController.navigate(MainRoutes.ListStudent.route){
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                menuItemsRegister[1] -> {
                    logs("go to subject", "click")
                    navController.navigate(MainRoutes.ListSubject.route){
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                menuItemsRegister[2] -> navController.navigate(MainRoutes.RegisterPartial.route)
                menuItemsRegister[3] -> { test() }
                menuItemsRegister[4] -> {test()}
            }
        }
    }
    CustomSpace(dimensionResource(id = R.dimen.margin_divided))
}

@Composable
private fun ControlAreaMenuScreen(
    uiData: ModelMenuUIData,
    navController: NavHostController,
) {
    val menuItemsControl = stringArrayResource(com.mx.liftechnology.data.R.array.menu_items_control)
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextSubHeader(MenuScreenObject.ADAPTER_CONTROL)
        MyGridScreen(uiData.controlItems, 200.dp) { selectedItem ->
            when (selectedItem.id) {
                menuItemsControl[0] -> navController.navigate(MainRoutes.RegisterSchool.route)
                menuItemsControl[1] -> navController.navigate(MainRoutes.Profile.route)
            }
        }
    }
    CustomSpace(dimensionResource(id = R.dimen.margin_between))
}