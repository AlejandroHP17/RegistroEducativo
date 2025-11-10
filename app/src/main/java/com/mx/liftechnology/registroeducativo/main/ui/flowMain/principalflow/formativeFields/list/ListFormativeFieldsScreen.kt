package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.formativeFields.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateSpinnerUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.GenericEmptyScreen
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.GenericListScreen
import com.mx.liftechnology.registroeducativo.main.util.navigateWithParams
import com.mx.liftechnology.registroeducativo.main.util.navigateWithState
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel

/**
 * The Subject List screen.
 *
 * @param navController The navigation controller.
 * @param listFormativeFieldsViewModel The ViewModel for this screen.
 */
@Composable
fun ListSubjectScreen(
    navController: NavHostController,
    listFormativeFieldsViewModel: ListFormativeFieldsViewModel = koinViewModel(),
) {
    val uiState by listFormativeFieldsViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by listFormativeFieldsViewModel.dataState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        listFormativeFieldsViewModel.getFormativeFields()
    }

    if (dataState.subjectList.isNullOrEmpty()) {
        GenericEmptyScreen(
            image = painterResource(id = R.drawable.ic_empty_subject),
            title = stringResource(R.string.empty_subject_1),
            description = stringResource(R.string.empty_subject_2),
            button = stringResource(R.string.add_button),
            onReturnClick = { navController.popBackStack() },
            onActionClick = { navController.navigateWithParams(MainRoutes.RegisterSubject.route) }
        )
    } else {
        GenericListScreen(
            title = stringResource(R.string.get_subject_name),
            textButton = stringResource(R.string.add_button),
            items = dataState.subjectListUI,
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
            onAction = { navController.navigateWithState(MainRoutes.RegisterSubject.route) }
        )
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}