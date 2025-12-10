package com.mx.liftechnology.registroeducativo.main.ui.workType.wotyByStudent

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
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.workType.WotyUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.workType.WotyUiData
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.generic.GenericJobsScreen
import com.mx.liftechnology.registroeducativo.main.util.navigation.AppRoutes
import org.koin.androidx.compose.koinViewModel

/**
 * Pantalla de asignaciones por estudiante.
 * 
 * Muestra las evaluaciones agrupadas por campo formativo y tipo de trabajo
 * para un estudiante específico.
 *
 * @param navController El controlador de navegación para gestionar los desplazamientos.
 * @param backStackEntry La entrada de la pila de retroceso para esta pantalla, contiene los datos del estudiante.
 * @param wotyByStudentViewModel El ViewModel para esta pantalla.
 */
@Composable
fun WotyByStudentScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    wotyByStudentViewModel: WotyByStudentViewModel = koinViewModel(),
) {

    val uiState by wotyByStudentViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by wotyByStudentViewModel.dataState.collectAsStateWithLifecycle()
    val studentJson = backStackEntry.arguments?.getString("student")

    LaunchedEffect(Unit) {
        val student: StudentDomain? = if (studentJson.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(studentJson, StudentDomain::class.java)
        }
        wotyByStudentViewModel.updateStudent(student)
        wotyByStudentViewModel.getListWotyFofi()
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
            complexCallbacks = WotyUiCallbacks(
                onExpandedTitle = { wotyByStudentViewModel.updateExpandedTitle(it) },
                onExpandedSubTitle = {subItem, parentItem -> wotyByStudentViewModel.updateExpandedSubTitle(subItem, parentItem ) },
            ),
            onAction = { navController.navigate(AppRoutes.Main.registerWoty(uiState.formativeFields))},
        )
    }
    LoadingAnimation(uiState.uiState == EnumUi.LOADING)
}


@Preview(showBackground = true)
@Composable
private fun WotyByStudentPreview(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {

        GenericJobsScreen(
            title = "Desconocido",
            description = stringResource(R.string.assignment_student_description),
            dataState = WotyUiData(),
            onReturnClick = {},
            complexCallbacks = WotyUiCallbacks(
                onExpandedTitle = {  },
                onExpandedSubTitle = { _, _ ->
                }
            ),
            onAction = { },
        )
    }
}