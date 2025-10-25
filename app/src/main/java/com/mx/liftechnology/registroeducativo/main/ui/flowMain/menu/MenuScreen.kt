package com.mx.liftechnology.registroeducativo.main.ui.flowMain.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelMenuDataData
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelMenuDialogUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelMenuStateUI
import com.mx.liftechnology.registroeducativo.main.ui.components.AlertDialogMenu
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderMenu
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.MyGridScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.TextSubHeader
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.menu.MenuScreenObject.CONTROL
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.menu.MenuScreenObject.REGISTER
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.util.navigateWithState
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel

/**
 * The main menu screen of the application.
 *
 * @param reload Whether to reload the data.
 * @param navController The navigation controller.
 * @param menuViewModel The ViewModel for this screen.
 * @param sharedViewModel The shared ViewModel.
 * @param onCloseSession A lambda to be invoked when the session is closed.
 */
@Composable
fun MenuScreen(
    reload: Boolean,
    navController: NavHostController,
    menuViewModel: MenuViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel,
    onCloseSession: () -> Unit,
) {

    val uiState by menuViewModel.uiState.collectAsStateWithLifecycle()
    val dialogState by menuViewModel.dialogState.collectAsStateWithLifecycle()
    val dataState by menuViewModel.dataState.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var isGroup by remember { mutableStateOf(false) }

    LaunchedEffect(reload) {
        if (dialogState.studentGroupItem.itemPartial == null || reload) {
            menuViewModel.getGroup()
            menuViewModel.getControlMenu()
        }
    }

    LaunchedEffect(uiState.uiState) {
        if (uiState.uiState == ModelStateUIEnum.UNAUTHORIZED) {
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
            uiDialog = dialogState,
            onShowDialog = {
                isGroup = true
                showDialog = true
            }
        )
        BodyMenuScreen(
            uiState = uiState,
            uiData = dataState,
            navController = { navController.navigateWithState(it) }
        )
    }

    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)

    if (showDialog) {
        AlertDialogMenu(
            uiDialog = dialogState,
            itemSelectedReturn = {
                menuViewModel.updateGroup(it)
                isGroup = false
            },
            itemSelectedPartialReturn = {
                menuViewModel.updatePartial(it)
                isGroup = true
            },
            selectType = isGroup,
            dismiss = { showDialog = false }
        )
    }
}

/**
 * An object that contains constants for the menu screen.
 */



object MenuScreenObject {
    const val ADAPTER_CONTROL = "Área de Control"
    const val ADAPTER_CONTROL_REGISTER = "Área de Registro y evaluación"
    const val REGISTER = "register"
    const val CONTROL = "control"
}

/**
 * The header of the main menu screen.
 *
 * @param uiDialog The state of the dialog.
 * @param onShowDialog A lambda to be invoked when the dialog is shown.
 */
@Composable
private fun HeaderMenuScreen(uiDialog: ModelMenuDialogUI, onShowDialog: (Boolean) -> Unit) {
    ComponentHeaderMenu(
        title = stringResource(R.string.menu_grettins, "Profesor"),
        body = uiDialog.studentGroupItem.nameItem ?: stringResource(R.string.menu_empty),
        partial = uiDialog.studentGroupItem.namePartial ?: stringResource(R.string.menu_partial),
        onClick = { onShowDialog(true) }
    )
    CustomSpace(dimensionResource(id = R.dimen.margin_between))
}

/**
 * The body of the main menu screen.
 *
 * @param uiState The UI state for the screen.
 * @param uiData The data state for the screen.
 * @param navController A lambda to be invoked to navigate to a different screen.
 */
@Composable
private fun BodyMenuScreen(
    uiState: ModelMenuStateUI,
    uiData: ModelMenuDataData,
    navController: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(listOf(REGISTER, CONTROL)) { _, section ->
            when (section) {

                REGISTER -> {
                    if (uiState.showControl) {
                        RegisterAreaMenuScreen(
                            uiData = uiData,
                            navController = { navController(it) },
                            test = { }
                        )
                    }
                }

                CONTROL -> {
                    ControlAreaMenuScreen(
                        uiData = uiData,
                        navController = { navController(it) },
                    )
                }
            }
        }
    }
}

/**
 * The register area of the main menu screen.
 *
 * @param uiData The data state for the screen.
 * @param navController A lambda to be invoked to navigate to a different screen.
 * @param test A lambda for testing purposes.
 */
@Composable
private fun RegisterAreaMenuScreen(
    uiData: ModelMenuDataData,
    navController: (String) -> Unit,
    test: () -> Unit,
) {
    val menuItemsRegister =
        stringArrayResource(com.mx.liftechnology.data.R.array.menu_items_register)
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextSubHeader(MenuScreenObject.ADAPTER_CONTROL_REGISTER)
        MyGridScreen(uiData.evaluationItems, 660.dp) { selectedItem ->
            when (selectedItem.id) {
                menuItemsRegister[0] -> navController(MainRoutes.ListStudent.route)
                menuItemsRegister[1] -> navController(MainRoutes.ListSubject.route)
                menuItemsRegister[2] -> navController(MainRoutes.Calendar.route)
                menuItemsRegister[3] -> navController(MainRoutes.RegisterPartial.route)

                menuItemsRegister[4] -> test()
            }
        }
    }
    CustomSpace(dimensionResource(id = R.dimen.margin_divided))
}

/**
 * The control area of the main menu screen.
 *
 * @param uiData The data state for the screen.
 * @param navController A lambda to be invoked to navigate to a different screen.
 */
@Composable
private fun ControlAreaMenuScreen(
    uiData: ModelMenuDataData,
    navController: (String) -> Unit,
) {
    val menuItemsControl = stringArrayResource(com.mx.liftechnology.data.R.array.menu_items_control)

    TextSubHeader(MenuScreenObject.ADAPTER_CONTROL)
    MyGridScreen(uiData.controlItems, 220.dp) { selectedItem ->
        when (selectedItem.id) {
            menuItemsControl[0] -> navController(MainRoutes.RegisterSchool.route)
            menuItemsControl[1] -> navController(MainRoutes.Profile.route)
        }
    }

    CustomSpace(dimensionResource(id = R.dimen.margin_between))
}