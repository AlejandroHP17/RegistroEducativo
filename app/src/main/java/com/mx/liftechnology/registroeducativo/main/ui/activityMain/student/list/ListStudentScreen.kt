package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelListStudentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBackWithout
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomCard
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.EmptyState
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import com.mx.liftechnology.registroeducativo.main.util.navigateWithParams
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel


@Composable
fun ListStudentScreen(
    navController: NavHostController,
    listStudentViewModel: ListStudentViewModel = koinViewModel(),
) {

    val uiState by listStudentViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by listStudentViewModel.dataState.collectAsStateWithLifecycle()

    // Cargar la lista de estudiantes cuando se monta la pantalla
    LaunchedEffect(Unit) {
        listStudentViewModel.getListStudent()
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        val (header, column, action) = createRefs()

        if (dataState.studentList.isNullOrEmpty()) {
            EmptyStudentState(
                onReturnClick = { navController.popBackStack() },
                onActionClick = {
                    navController.navigateWithParams(
                        MainRoutes.RegisterStudent.createRoutes(
                            null
                        )
                    )
                }
            )
        } else {

            Box(
                modifier = Modifier.constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                HeaderListStudent(onReturnClick = { navController.popBackStack() })
            }

            Box(
                modifier = Modifier.constrainAs(column) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(action.top)
                    height = Dimension.fillToConstraints
                }) {

                BodyListStudent(
                    dataState = dataState,
                    onNavigate = {
                        navController.navigateWithParams(
                            MainRoutes.RegisterStudent.createRoutes(
                                listStudentViewModel.getStudent(it)
                            )
                        )
                    }
                )
            }

            Column(
                modifier = Modifier.constrainAs(action) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                ActionListStudent {
                    navController.navigateWithParams(
                        MainRoutes.RegisterStudent.createRoutes(
                            null
                        )
                    )
                }
            }
        }
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

@Composable
fun EmptyStudentState(
    onReturnClick: () -> Unit,
    onActionClick: () -> Unit,
) {
    EmptyState(
        image = painterResource(id = R.drawable.ic_empty_student),
        title = stringResource(R.string.empty_student_1),
        description = stringResource(R.string.empty_student_2),
        button = stringResource(R.string.add_button),
        onReturnClick = { onReturnClick() },
        onActionClick = { onActionClick() }
    )
}

@Composable
private fun HeaderListStudent(
    onReturnClick: () -> Unit,
) {
    ComponentHeaderBackWithout(
        title = stringResource(R.string.get_student_name),
        onReturnClick = { onReturnClick() }
    )
}

@Composable
private fun BodyListStudent(
    dataState: ModelListStudentDataState,
    onNavigate: (ModelCustomCard) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        itemsIndexed(dataState.studentListUI, key = { _, item -> item.id }) { _, item ->
            CustomCard(
                item = item,
                onItemClick = { onNavigate(item) },
                onItemMore = { }
            )
        }
    }
}


@Composable
private fun ActionListStudent(
    onActionClick: () -> Unit,
) {
    CustomSpace(dimensionResource(R.dimen.margin_divided))
    ButtonAction(
        containerColor = colorAction,
        text = stringResource(R.string.add_button),
        onActionClick = { onActionClick() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}