package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.assignment

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
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelAssignmentUiCallbacks
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.GenericJobsScreen
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun AssignmentStudentScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    assignmentStudentViewModel: AssignmentStudentViewModel = koinViewModel(),
) {

    val uiState by assignmentStudentViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by assignmentStudentViewModel.dataState.collectAsStateWithLifecycle()
    val studentJson = backStackEntry.arguments?.getString("student")

    LaunchedEffect(Unit) {
        val student: ModelStudentDomain? = if (studentJson.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(studentJson, ModelStudentDomain::class.java)
        }
        assignmentStudentViewModel.updateStudent(student)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {

        GenericJobsScreen(
            title = uiState.student?.name ?: "Desconocido",
            description = stringResource(R.string.assignment_student_description),
            dataState = dataState,
            onReturnClick = {navController.popBackStack()},
            complexCallbacks = ModelAssignmentUiCallbacks(
                onExpandedTitle = { assignmentStudentViewModel.updateExpandedTitle(it) },
                onExpandedSubTitle = { // assignmentSubjectViewModel.updateExpandedSubTitle(it)
                },
                onItemClick = {}
            ),
            onAction = { navController.navigate(MainRoutes.RegisterAssignment.createRoutes(uiState.subject))},
        )
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}
