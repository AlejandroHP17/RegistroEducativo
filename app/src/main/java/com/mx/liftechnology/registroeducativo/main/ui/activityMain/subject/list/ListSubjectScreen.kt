package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelListSubjectUIState
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomCard
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.EmptyState
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel


@Composable
fun ListSubjectScreen(
    navController: NavHostController,
    listSubjectViewModel: ListSubjectViewModel = koinViewModel(),
) {
    // Cargar la lista de estudiantes cuando se monta la pantalla
    LaunchedEffect(Unit) {
        listSubjectViewModel.getSubject()
    }

    val uiState by listSubjectViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        if (uiState.subjectList.isNullOrEmpty()) {
            EmptySubjectState(navController)
        } else {

            Column(modifier = Modifier.fillMaxSize()) {
                HeaderListSubject(navController = navController)

                BodyListSubject(
                    uiState = uiState,
                )

                Spacer(modifier = Modifier.weight(1f))

                ActionListSubject(navController = navController)
            }
        }
    }
    LoadingAnimation(uiState.isLoading)
}

@Composable
fun EmptySubjectState(navController: NavController) {
    EmptyState(
        image = painterResource(id = R.drawable.ic_empty_subject),
        title = stringResource(R.string.empty_subject_1),
        description = stringResource(R.string.empty_subject_2),
        button = stringResource(R.string.add_button),
        onReturnClick = { navController.popBackStack() },
        onActionClick = { navController.navigate(MainRoutes.RegisterSubject.route) }
    )
}


@Composable
private fun HeaderListSubject(navController: NavHostController) {
    ComponentHeaderBack(
        title = stringResource(R.string.get_subject_name),
        body = stringResource(R.string.tools_empty)
    ) { navController.popBackStack() }
}

@Composable
private fun BodyListSubject(
    uiState: ModelListSubjectUIState,
) {
    LazyColumn(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        itemsIndexed(uiState.subjectListUI) { _, item ->
            CustomCard(
                item = item,
                onItemClick = {
                    /*val navigate = MainRoutes.EDIT_STUDENT.createRoute(student)
                    navController.navigate(navigate)*/
                },
                onItemMore = { student ->
                }
            )
        }
    }
}

@Composable
private fun ActionListSubject(
    navController: NavController,
) {
    ButtonAction(
        containerColor = color_action,
        text = stringResource(R.string.add_button),
        onActionClick = { navController.navigate(MainRoutes.RegisterSubject.route) }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}