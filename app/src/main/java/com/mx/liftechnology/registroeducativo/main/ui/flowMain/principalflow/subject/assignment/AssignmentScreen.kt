package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.assignment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelAssignmentUiCallbacks
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.GenericJobsScreen
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun AssignmentScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    assignmentViewModel: AssignmentViewModel = koinViewModel(),
) {

    val uiState by assignmentViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by assignmentViewModel.dataState.collectAsStateWithLifecycle()
    val subjectJson = backStackEntry.arguments?.getString("subject")

    LaunchedEffect(Unit) {
        val subject: ModelFormatSubjectDomain? = if (subjectJson.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(subjectJson, ModelFormatSubjectDomain::class.java)
        }
        assignmentViewModel.updateSubject(subject)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {

        GenericJobsScreen(
            title = uiState.subject?.name ?: "Desconocido",
            description = stringResource(R.string.assignment_description),
            dataState = dataState,
            onReturnClick = {navController.popBackStack()},
            complexCallbacks = ModelAssignmentUiCallbacks(
                onExpandedTitle = { assignmentViewModel.updateExpandedTitle(it) },
                onExpandedSubTitle = { // assignmentViewModel.updateExpandedSubTitle(it)
                },
                onItemClick = {}
            ),
            onAction = { navController.navigate(MainRoutes.RegisterAssignment.createRoutes(uiState.subject))},
        )
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}
