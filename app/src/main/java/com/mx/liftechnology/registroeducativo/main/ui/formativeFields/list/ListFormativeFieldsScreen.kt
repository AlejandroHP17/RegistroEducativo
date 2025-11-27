package com.mx.liftechnology.registroeducativo.main.ui.formativeFields.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateSpinnerUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelListFormativeFieldsDataState
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.generic.GenericEmptyScreen
import com.mx.liftechnology.registroeducativo.main.ui.generic.GenericListScreen
import com.mx.liftechnology.registroeducativo.main.util.navigateWithParams
import com.mx.liftechnology.registroeducativo.main.util.navigateWithState
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel

/**
 * The Formative Fields List screen.
 *
 * @param navController The navigation controller.
 * @param listFormativeFieldsViewModel The ViewModel for this screen.
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
            image = painterResource(id = R.drawable.ic_empty_subject),
            title = stringResource(R.string.empty_subject_1),
            description = stringResource(R.string.empty_subject_2),
            button = stringResource(R.string.add_button),
            onReturnClick = { navController.popBackStack() },
            onActionClick = { navController.navigateWithParams(MainRoutes.RegisterFormativeField.route) }
        )
    } else {
        GenericListScreen(
            title = stringResource(R.string.get_subject_name),
            textButton = stringResource(R.string.add_button),
            items = dataState.formativeFieldsListUI,
            onReturnClick = { navController.popBackStack() },
            callbacks = ModelStateSpinnerUI(
                onItemClick = {
                    navController.navigateWithParams(
                        MainRoutes.AssignmentSubject.createRoutes(
                            listFormativeFieldsViewModel.getFormativeFields(it)
                        )
                    )
                },
                onEdit = {},
                onDelete = { listFormativeFieldsViewModel.deleteFormativeField(it) }
            ),
            onAction = { navController.navigateWithState(MainRoutes.RegisterFormativeField.route) }
        )
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}


@Preview(showBackground = true)
@Composable()
private fun ListFormativeFieldsPreview(){

    val dataState = ModelListFormativeFieldsDataState(
        formativeFieldsList = listOf(
            ModelFormatFormativeFieldsDomain(
                position = 1,
                name = "test",
                percent = "100",
                formativeFieldId = 1
            )
        )
    )

    if (dataState.formativeFieldsList.isNullOrEmpty()) {
        GenericEmptyScreen(
            image = painterResource(id = R.drawable.ic_empty_subject),
            title = stringResource(R.string.empty_subject_1),
            description = stringResource(R.string.empty_subject_2),
            button = stringResource(R.string.add_button),
            onReturnClick = { },
            onActionClick = { }
        )
    } else {
        GenericListScreen(
            title = stringResource(R.string.get_subject_name),
            textButton = stringResource(R.string.add_button),
            items = dataState.formativeFieldsListUI,
            onReturnClick = { },
            callbacks = ModelStateSpinnerUI(
                onItemClick = {},
                onEdit = {},
                onDelete = { }
            ),
            onAction = { }
        )
    }
}