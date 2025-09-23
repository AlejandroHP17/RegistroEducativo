package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.assignment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelAssignmentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelAssignmentUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelAssignmentUiState
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComplexCard
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
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
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderAssignment(
                uiState =  uiState,
                navController = navController)
            BodyAssignment(
                dataState =  dataState,
                complexCallbacks = ModelAssignmentUiCallbacks(
                    onExpandedTitle = { assignmentViewModel.updateExpandedTitle(it) },
                    onExpandedSubTitle = { // assignmentViewModel.updateExpandedSubTitle(it)
                         },
                    onItemClick = {}
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            ActionAssignment {
                navController.navigate(MainRoutes.RegisterAssignment.createRoutes(uiState.subject))
            }
        }

    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

@Composable
private fun HeaderAssignment(
    uiState: ModelAssignmentUiState,
    navController: NavHostController) {
    ComponentHeaderBack(
        title = uiState.subject?.name ?: "Desconocido",
        body = stringResource(R.string.assignment_description)
    ) { navController.popBackStack() }
}

@Composable
private fun BodyAssignment(
    dataState: ModelAssignmentDataState,
    complexCallbacks: ModelAssignmentUiCallbacks
    ){
    ComplexCard(
        item = dataState.dataCard,
        complexCallbacks = ModelAssignmentUiCallbacks(
            onExpandedTitle = { complexCallbacks.onExpandedTitle(it) },
            onExpandedSubTitle = { complexCallbacks.onExpandedSubTitle(it) },
            onItemClick = { complexCallbacks.onItemClick(it) }
        )
    )
}

@Composable
private fun ActionAssignment(
    onActionClick:() -> Unit
) {
    ButtonAction(
        containerColor = colorAction,
        text = stringResource(R.string.add_button),
        onActionClick = { onActionClick() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}