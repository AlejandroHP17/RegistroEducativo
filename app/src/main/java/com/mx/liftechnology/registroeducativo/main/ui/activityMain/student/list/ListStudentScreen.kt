package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.list

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
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelListStudentUIState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard
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
fun ListStudentScreen(
    navController: NavHostController,
    listStudentViewModel: ListStudentViewModel = koinViewModel(),
) {
    // Cargar la lista de estudiantes cuando se monta la pantalla
    LaunchedEffect(Unit) {
        listStudentViewModel.getListStudent()
    }

    val uiState by listStudentViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        if (uiState.studentList.isNullOrEmpty()) {
            EmptyStudentState(navController)
        } else {

            Column(modifier = Modifier.fillMaxSize()) {
                HeaderListStudent(navController = navController)

                BodyListStudent(
                    uiState = uiState,
                    onNavigate = {
                        navController.navigate(
                            MainRoutes.RegisterStudent.createRoutes(
                                listStudentViewModel.getStudent(it)
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                ActionListStudent { navController.navigate(MainRoutes.RegisterStudent.createRoutes(null)) }
            }
        }
    }
    LoadingAnimation(uiState.isLoading)
}

@Composable
fun EmptyStudentState(navController: NavController) {
    EmptyState(
        image = painterResource(id = R.drawable.ic_empty_student),
        title = stringResource(R.string.empty_student_1),
        description = stringResource(R.string.empty_student_2),
        button = stringResource(R.string.add_button),
        onReturnClick = { navController.popBackStack() },
        onActionClick = { navController.navigate(MainRoutes.RegisterStudent.route) }
    )
}

@Composable
private fun HeaderListStudent(navController: NavHostController) {
    ComponentHeaderBack(
        title = stringResource(R.string.get_student_name),
        body = stringResource(R.string.tools_empty)
    ) { navController.popBackStack() }
}

@Composable
private fun BodyListStudent(
    uiState: ModelListStudentUIState,
    onNavigate: (ModelCustomCard) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        itemsIndexed(uiState.studentListUI) { _, item ->
            CustomCard(
                item = item,
                onItemClick = {
                    onNavigate(item)
                },
                onItemMore = {

                }
            )
        }
    }
}


@Composable
private fun ActionListStudent(
    onActionClick: () -> Unit,
) {
    ButtonAction(
        containerColor = color_action,
        text = stringResource(R.string.add_button),
        onActionClick = { onActionClick() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}