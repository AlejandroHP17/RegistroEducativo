package com.mx.liftechnology.registroeducativo.main.ui.student.wotyfofi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelWotyFofiDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelWotyFofiUiCallbacks
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.generic.GenericJobsScreen
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel

/**
 * The Student Assignment screen.
 *
 * @param navController The navigation controller.
 * @param backStackEntry The back stack entry for this screen.
 * @param wotyFofiStudentViewModel The ViewModel for this screen.
 */
@Composable
fun WotyFofiStudentScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    wotyFofiStudentViewModel: WotyFofiStudentViewModel = koinViewModel(),
) {

    val uiState by wotyFofiStudentViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by wotyFofiStudentViewModel.dataState.collectAsStateWithLifecycle()
    val studentJson = backStackEntry.arguments?.getString("student")

    LaunchedEffect(Unit) {
        val student: ModelStudentDomain? = if (studentJson.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(studentJson, ModelStudentDomain::class.java)
        }
        wotyFofiStudentViewModel.updateStudent(student)
        wotyFofiStudentViewModel.getListWotyFofi()
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
            complexCallbacks = ModelWotyFofiUiCallbacks(
                onExpandedTitle = { wotyFofiStudentViewModel.updateExpandedTitle(it) },
                onExpandedSubTitle = {
                },
                onItemClick = {}
            ),
            onAction = { navController.navigate(MainRoutes.RegisterAssignment.createRoutes(uiState.subject))},
        )
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}


@Preview(showBackground = true)
@Composable
private fun WotyFofiStudentPreview(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {

        GenericJobsScreen(
            title = "Desconocido",
            description = stringResource(R.string.assignment_student_description),
            dataState = ModelWotyFofiDataState(),
            onReturnClick = {},
            complexCallbacks = ModelWotyFofiUiCallbacks(
                onExpandedTitle = {  },
                onExpandedSubTitle = {
                },
                onItemClick = {}
            ),
            onAction = { },
        )
    }
}