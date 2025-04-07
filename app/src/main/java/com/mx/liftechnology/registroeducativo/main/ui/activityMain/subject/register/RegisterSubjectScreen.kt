package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.mx.liftechnology.core.network.callapi.ResponseGetListAssessmentType
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterSubjectUIState
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.EvaluationPercentList
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.SpinnerOutlinedTextField
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterSubjectScreen(
    navController: NavHostController,
    registerSubjectViewModel: RegisterSubjectViewModel = koinViewModel(),
) {

    val uiState by registerSubjectViewModel.uiState.collectAsState()
    // Llamadas a servicios cuando se monta la pantalla
    LaunchedEffect(Unit) {
        registerSubjectViewModel.getListAssessmentType()
    }
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.popBackStack()
        }
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        val (header, body, column, action) = createRefs()

        Column(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) { HeaderRegisterSubject(navigate = { navController.popBackStack() }) }

        Column(
            modifier = Modifier.constrainAs(body) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            BodyRegisterSubject(
                uiState = uiState,
                onSubjectChanged = { registerSubjectViewModel.onSubjectChanged(it) },
                onOptionsChanged = { registerSubjectViewModel.onOptionsChanged(it) }
            )
        }

        Column(
            modifier = Modifier.constrainAs(column) {
                top.linkTo(body.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(action.top)
                height = Dimension.fillToConstraints
            }) {
            if (uiState.options.valueText.isNotEmpty() && uiState.options.valueText.toInt() > 0) {
                ColumnRegisterSubject(
                    uiState = uiState,
                    onNameChange = { registerSubjectViewModel.onNameChange(it) },
                    onPercentChange = { registerSubjectViewModel.onPercentChange(it) }
                )
            }
        }

        Column(
            modifier = Modifier.constrainAs(action) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            ActionRegisterSubject { registerSubjectViewModel.validateFieldsCompose() }
        }
    }
    LoadingAnimation(uiState.isLoading)
}

@Composable
private fun HeaderRegisterSubject(
    navigate: () -> Unit,
) {
    ComponentHeaderBack(
        title = stringResource(R.string.register_subject_name),
        body = stringResource(R.string.register_subject_name_description),
        onReturnClick = { navigate() })
}

@Composable
private fun BodyRegisterSubject(
    uiState: ModelRegisterSubjectUIState,
    onSubjectChanged: (String) -> Unit,
    onOptionsChanged: (String) -> Unit,
) {
    BoxEditTextGeneric(
        value = uiState.subject,
        enable = true,
        label = stringResource(id = R.string.register_subject_field),
    ) { onSubjectChanged(it) }

    CustomSpace(dimensionResource(R.dimen.margin_between))

    TextBody(stringResource(R.string.register_subject_name_description_2))

    CustomSpace(dimensionResource(R.dimen.margin_divided))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            CustomSpace(dimensionResource(R.dimen.margin_outer))
            TextBody(stringResource(R.string.register_subject_name_description_3))
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            SpinnerOutlinedTextField(
                options = uiState.listOptions,
                selectedOption = uiState.options,
                read = uiState.read,
                label = stringResource(id = R.string.register_subject_options),
                onOptionSelected = { onOptionsChanged(it) }
            )
        }
    }
    CustomSpace(dimensionResource(R.dimen.margin_between))
}


@Composable
private fun ColumnRegisterSubject(
    uiState: ModelRegisterSubjectUIState,
    onNameChange: (Pair<ResponseGetListAssessmentType?, Int>) -> Unit,
    onPercentChange: (Pair<String, Int>) -> Unit,
) {
    EvaluationPercentList(
        listWorkMethods = uiState.listWorkMethods,
        items = uiState.listAdapter!!,
        onNameChange = { onNameChange(it) },
        onPercentChange = { onPercentChange(it) }
    )
}

@Composable
private fun ActionRegisterSubject(
    validateFieldsCompose: () -> Unit,
) {
    ButtonAction(
        containerColor = color_action,
        text = stringResource(R.string.add_button),
        onActionClick = { validateFieldsCompose() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}