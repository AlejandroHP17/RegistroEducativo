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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomCard
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.EmptyState
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel


@Preview(showBackground = true)
@Composable
fun PreviewListStudentScreen() {
    val navController = rememberNavController() // Crea un controlador de navegación simulado
    ListStudentScreen(navController)
}

@Composable
fun ListStudentScreen(
    navController: NavHostController,
    listStudentViewModel: ListStudentViewModel = koinViewModel()
) {
    // Cargar la lista de estudiantes cuando se monta la pantalla
    LaunchedEffect(Unit) {
        listStudentViewModel.getListStudentCompose()
    }

    val uiState by listStudentViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.margin_outer))
    ) {
        if (uiState.studentList.isNullOrEmpty()) {
            EmptyStudentState(navController)
        } else {

            Column(
                modifier = Modifier.fillMaxSize()
            )
            {
                ComponentHeaderBack(
                    title = stringResource(R.string.get_student_name),
                    body = ""
                ) {  navController.popBackStack() }

                LazyColumn(
                    modifier = Modifier.wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
                ) {
                    itemsIndexed(uiState.studentListUI) { _, item ->
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

                Spacer(modifier = Modifier.weight(1f))

                ButtonAction(
                    containerColor = color_action,
                    text = stringResource(R.string.add_button),
                    onActionClick = { navController.navigate(MainRoutes.REGISTER_STUDENT.route) }
                )

                CustomSpace(dimensionResource(R.dimen.margin_divided))
            }

        }
        LoadingAnimation(uiState.isLoading)
    }
}

@Composable
fun EmptyStudentState(navController: NavController) {
    EmptyState(
        image = painterResource(id = R.drawable.ic_empty_student),
        title = stringResource(R.string.empty_student_1),
        description = stringResource(R.string.empty_student_2),
        button = stringResource(R.string.add_button),
        onReturnClick = { navController.popBackStack() },
        onActionClick = { navController.navigate(MainRoutes.REGISTER_STUDENT.route) }
    )
}

