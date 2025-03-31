package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.AlertDialogMenu
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderMenu
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.MyGridScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.TextSubHeader
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel


@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    val navController = rememberNavController() // Crea un controlador de navegación simulado
    MenuScreen(navController)
}

@Composable
fun MenuScreen(
    navController: NavHostController,
    menuViewModel: MenuViewModel = koinViewModel()
){
    // Llamadas a servicios cuando se monta la pantalla
    LaunchedEffect(Unit) {
        menuViewModel.getGroupCompose()
        menuViewModel.getControlMenuCompose()
    }

    val uiState by menuViewModel.uiState.collectAsState()
    val menuItemsControl = stringArrayResource(com.mx.liftechnology.data.R.array.menu_items_control)
    val menuItemsRegister = stringArrayResource(com.mx.liftechnology.data.R.array.menu_items_register)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.margin_outer))
        )
        {
            item {
                ComponentHeaderMenu(
                    title = stringResource(R.string.menu_grettins, "Profesor"),
                    body = uiState.studentGroupItem.nameItem ?:
                        stringResource(R.string.menu_empty)
                ) {
                    menuViewModel.showDialog(true)
                }
                CustomSpace(dimensionResource(id = R.dimen.margin_between))
            }

            if(uiState.showControl){
                item {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()) {
                        TextSubHeader(MenuScreenObject.ADAPTER_CONTROL_REGISTER)
                        MyGridScreen(uiState.evaluationItems, 628.dp){selectedItem ->

                            when (selectedItem.id) {
                                menuItemsRegister[0] -> navController.navigate(MainRoutes.LIST_STUDENT.route)
                                menuItemsRegister[1] -> navController.navigate(MainRoutes.LIST_SUBJECT.route)
                                menuItemsRegister[2] -> navController.navigate(MainRoutes.REGISTER_PARTIAL.route)
                                menuItemsRegister[3] -> {}
                                menuItemsRegister[4] -> {}
                            }


                        }
                    }
                    CustomSpace(dimensionResource(id = R.dimen.margin_divided))
                }
            }

            item {
                Column (modifier = Modifier
                    .fillMaxWidth()) {
                    TextSubHeader(MenuScreenObject.ADAPTER_CONTROL)
                    MyGridScreen(uiState.controlItems, 200.dp){ selectedItem ->

                        when (selectedItem.id) {
                            menuItemsControl[0] -> navController.navigate(MainRoutes.REGISTER_SCHOOL.route)
                            menuItemsControl[1] -> {}
                        }

                    }
                }
                CustomSpace(dimensionResource(id = R.dimen.margin_between))
            }
        }

        if(uiState.showDialog) AlertDialogMenu(
            uiState = uiState,
            itemSelectedReturn = {
                menuViewModel.updateGroupCompose(it)
            },
            dismiss = {menuViewModel.showDialog(false)}
        )

        LoadingAnimation(uiState.isLoading)
    }
}

object MenuScreenObject {
    const val ADAPTER_CONTROL = "Área de Control"
    const val ADAPTER_CONTROL_REGISTER = "Área de Registro y evaluación"
}