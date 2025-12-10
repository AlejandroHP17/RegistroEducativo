package com.mx.liftechnology.registroeducativo.main.ui.formativeFields.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.SpinnerUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.ListFormativeFieldsUiData
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.generic.GenericEmptyScreen
import com.mx.liftechnology.registroeducativo.main.ui.generic.GenericListScreen
import com.mx.liftechnology.registroeducativo.main.util.navigateWithParams
import com.mx.liftechnology.registroeducativo.main.util.navigateWithState
import com.mx.liftechnology.registroeducativo.main.util.navigation.AppRoutes
import org.koin.androidx.compose.koinViewModel

/**
 * Pantalla de lista de materias formativas.
 *
 * @param navController El controlador de navegación.
 * @param listFormativeFieldsViewModel El ViewModel para esta pantalla.
 */
@Composable
fun ListFormativeFieldsScreen(
    navController: NavHostController,
    listFormativeFieldsViewModel: ListFormativeFieldsViewModel = koinViewModel(),
) {
    val uiState by listFormativeFieldsViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by listFormativeFieldsViewModel.dataState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        listFormativeFieldsViewModel.getFormativeFields()
    }

    if (dataState.formativeFieldsList.isNullOrEmpty()) {
        GenericEmptyScreen(
            image = painterResource(id = R.drawable.ic_empty_formative_field),
            title = stringResource(R.string.empty_formative_field_1),
            description = stringResource(R.string.empty_formative_field_2),
            button = stringResource(R.string.add_button),
            onReturnClick = { navController.popBackStack() },
            onActionClick = { navController.navigateWithParams(AppRoutes.Main.REGISTER_FORMATIVE_FIELD) }
        )
    } else {
        GenericListScreen(
            title = stringResource(R.string.get_formative_field_name),
            textButton = stringResource(R.string.add_button),
            items = dataState.formativeFieldsListUI,
            onReturnClick = { navController.popBackStack() },
            callbacks = SpinnerUiCallbacks(
                onItemClick = {
                    navController.navigateWithParams(
                        AppRoutes.Main.wotyFormativeField(
                            listFormativeFieldsViewModel.getFormativeFields(it)
                        )
                    )
                },
                onEdit = {},
                onDelete = { listFormativeFieldsViewModel.deleteFormativeField(it) }
            ),
            onAction = { navController.navigateWithState(AppRoutes.Main.REGISTER_FORMATIVE_FIELD) }
        )
    }
    LoadingAnimation(uiState.uiState == EnumUi.LOADING)
}


@Preview(showBackground = true)
@Composable()
private fun ListFormativeFieldsPreview(){

    val dataState = ListFormativeFieldsUiData(
        formativeFieldsList = listOf(
            FormativeFieldDomainPar(
                position = 1,
                name = "test",
                percent = "100",
                formativeFieldId = 1
            )
        )
    )

    if (dataState.formativeFieldsList.isNullOrEmpty()) {
        GenericEmptyScreen(
            image = painterResource(id = R.drawable.ic_empty_formative_field),
            title = stringResource(R.string.empty_formative_field_1),
            description = stringResource(R.string.empty_formative_field_2),
            button = stringResource(R.string.add_button),
            onReturnClick = { },
            onActionClick = { }
        )
    } else {
        GenericListScreen(
            title = stringResource(R.string.get_formative_field_name),
            textButton = stringResource(R.string.add_button),
            items = dataState.formativeFieldsListUI,
            onReturnClick = { },
            callbacks = SpinnerUiCallbacks(
                onItemClick = {},
                onEdit = {},
                onDelete = { }
            ),
            onAction = { }
        )
    }
}