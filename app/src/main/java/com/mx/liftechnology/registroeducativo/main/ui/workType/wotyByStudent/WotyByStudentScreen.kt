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
import android.net.Uri
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
import com.mx.liftechnology.registroeducativo.main.util.extractQueryParam
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

    LaunchedEffect(Unit) {
        // Obtener parámetros desde arguments (definidos en la ruta)
        var studentJson = backStackEntry.arguments?.getString("student")
        var date = backStackEntry.arguments?.getString("date")
        
        // Si date no está en arguments, intentar parsearlo desde savedStateHandle
        // Esto puede ser necesario si la ruta no coincide exactamente
        if (date == null) {
            val savedStateHandle = backStackEntry.savedStateHandle
            val routeFromSavedState = savedStateHandle.get<String>("full_route")
            
            // Si tenemos la ruta completa guardada, parsear los query parameters
            routeFromSavedState?.let { route ->
                if (route.contains("?")) {
                    date = extractQueryParam(route, "date")
                }
            }
        }
        
        val student: StudentDomain? = getStudent(studentJson)
        
        wotyByStudentViewModel.updateStudent(student)
        wotyByStudentViewModel.updateDate(date)
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

private fun getStudent(studentJson: String?): StudentDomain? {
   return if (studentJson.isNullOrEmpty()) {
        null
    } else {
        Gson().fromJson(studentJson, StudentDomain::class.java)
    }
}