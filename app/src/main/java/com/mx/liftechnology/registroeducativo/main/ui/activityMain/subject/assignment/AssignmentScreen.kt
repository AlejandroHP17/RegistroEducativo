package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.assignment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelAssignmentUIState
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun AssignmentScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    assignmentViewModel: AssignmentViewModel = koinViewModel(),
) {

    val uiState by assignmentViewModel.uiState.collectAsState()
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


            Spacer(modifier = Modifier.weight(1f))

            ActionAssignment {
                navController.navigate(MainRoutes.RegisterAssignment.createRoutes(uiState.subject))
            }
        }

    }
    LoadingAnimation(uiState.isLoading)
}

@Composable
private fun HeaderAssignment(
    uiState: ModelAssignmentUIState,
    navController: NavHostController) {
    ComponentHeaderBack(
        title = uiState.subject?.name ?: "Desconocido",
        body = stringResource(R.string.assignment_description)
    ) { navController.popBackStack() }
}

@Composable
private fun ActionAssignment(
    onActionClick:() -> Unit
) {
    ButtonAction(
        containerColor = color_action,
        text = stringResource(R.string.add_button),
        onActionClick = { onActionClick() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}