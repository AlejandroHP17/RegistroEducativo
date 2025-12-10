package com.mx.liftechnology.registroeducativo.main.ui.workType.wotyByFormativeField

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
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.workType.WotyFofiUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.workType.WotyFofiUiData
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.generic.GenericJobsScreen
import com.mx.liftechnology.registroeducativo.main.util.navigation.AppRoutes
import org.koin.androidx.compose.koinViewModel

/**
 * Pantalla de asignación de materia.
 *
 * @param navController El controlador de navegación.
 * @param backStackEntry La entrada del back stack para esta pantalla.
 * @param wotyByFormativeFieldViewModel El ViewModel para esta pantalla.
 */
@Composable
fun WotyByFormativeFieldScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    wotyByFormativeFieldViewModel: WotyByFormativeFieldViewModel = koinViewModel(),
) {

    val uiState by wotyByFormativeFieldViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by wotyByFormativeFieldViewModel.dataState.collectAsStateWithLifecycle()
    val formativeFieldJson = backStackEntry.arguments?.getString("formativeField")

    LaunchedEffect(Unit) {
        val formativeField: FormativeFieldDomainPar? = if (formativeFieldJson.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(formativeFieldJson, FormativeFieldDomainPar::class.java)
        }
        wotyByFormativeFieldViewModel.updateFormativeField(formativeField)
        wotyByFormativeFieldViewModel.getListWotyFormativeField()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {

        GenericJobsScreen(
            title = uiState.formativeFields?.name ?: "Desconocido",
            description = stringResource(R.string.assignment_formative_field_description),
            dataState = dataState,
            onReturnClick = {navController.popBackStack()},
            complexCallbacks = WotyFofiUiCallbacks(
                onExpandedTitle = { wotyByFormativeFieldViewModel.updateExpandedTitle(it) },
                onExpandedSubTitle = {subItem, parentItem -> wotyByFormativeFieldViewModel.updateExpandedSubTitle(subItem, parentItem ) },
            ),
            onAction = { navController.navigate(AppRoutes.Main.registerWoty(uiState.formativeFields))},
        )
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}


@Preview(showBackground = true)
@Composable
private fun WotyByFormativeFieldsPreview(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {

        GenericJobsScreen(
            title = "Desconocido",
            description = stringResource(R.string.assignment_student_description),
            dataState = WotyFofiUiData(),
            onReturnClick = {},
            complexCallbacks = WotyFofiUiCallbacks(
                onExpandedTitle = {  },
                onExpandedSubTitle = { subItem, parentItem ->
                }
            ),
            onAction = { },
        )
    }
}
