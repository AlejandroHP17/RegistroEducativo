package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelListSubjectUIState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBackWithout
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

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        val (header, column, action) = createRefs()

        if (uiState.subjectList.isNullOrEmpty()) {
            EmptySubjectState(
                onReturnClick = {navController.popBackStack()},
                onActionClick = {navController.navigate(MainRoutes.RegisterSubject.route) }
            )
        } else {

            Column(
                modifier = Modifier.constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {HeaderListSubject(navController = navController)}

            Column(
                modifier = Modifier.constrainAs(column) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(action.top)
                    height = Dimension.fillToConstraints
                }) {
                BodyListSubject(
                    uiState = uiState,
                    onNavigate = {
                        navController.navigate(MainRoutes.Assignment.createRoutes(listSubjectViewModel.getSubject(it)))
                    }
                )
            }

            Column(
                modifier = Modifier.constrainAs(action) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                ActionListSubject(navController = navController)
            }
        }
    }
    LoadingAnimation(uiState.isLoading)
}

@Composable
fun EmptySubjectState(
    onReturnClick:() ->Unit,
    onActionClick:() ->Unit
) {
    EmptyState(
        image = painterResource(id = R.drawable.ic_empty_subject),
        title = stringResource(R.string.empty_subject_1),
        description = stringResource(R.string.empty_subject_2),
        button = stringResource(R.string.add_button),
        onReturnClick = { onReturnClick() },
        onActionClick = { onActionClick() }
    )
}


@Composable
private fun HeaderListSubject(navController: NavHostController) {
    ComponentHeaderBackWithout(
        title = stringResource(R.string.get_subject_name),
    ) { navController.popBackStack() }
}

@Composable
private fun BodyListSubject(
    uiState: ModelListSubjectUIState,
    onNavigate:(ModelCustomCard) -> Unit
) {
    LazyColumn(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        itemsIndexed(uiState.subjectListUI) { _, item ->
            CustomCard(
                item = item,
                onItemClick = {
                    onNavigate(item)
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
    CustomSpace(dimensionResource(R.dimen.margin_divided))
    ButtonAction(
        containerColor = color_action,
        text = stringResource(R.string.add_button),
        onActionClick = { navController.navigate(MainRoutes.RegisterSubject.route) }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}