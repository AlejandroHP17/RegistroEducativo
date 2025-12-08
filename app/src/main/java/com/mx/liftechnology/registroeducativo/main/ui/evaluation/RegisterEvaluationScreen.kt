package com.mx.liftechnology.registroeducativo.main.ui.evaluation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelRegex.COMPLEX_TEXT
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.event.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.evaluation.RegisterEvaluationUiData
import com.mx.liftechnology.registroeducativo.main.model.evaluation.RegisterEvaluationUiState
import com.mx.liftechnology.registroeducativo.main.model.share.ModelCustomCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ComponentHeaderBackWithout
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.calendars.DateSimplePickerDialog
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.EvaluationStudentList
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.form.DropdownTextField
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import org.koin.androidx.compose.koinViewModel

/**
 * The Assignment Registration screen.
 *
 * @param navController The navigation controller.
 * @param backStackEntry The back stack entry for this screen.
 * @param registerEvaluationViewModel The ViewModel for this screen.
 * @param sharedViewModel The shared ViewModel.
 */
@Composable
fun RegisterEvaluationScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    registerEvaluationViewModel: RegisterEvaluationViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel,
) {

    val uiState by registerEvaluationViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by registerEvaluationViewModel.dataState.collectAsStateWithLifecycle()
    val dialogState by registerEvaluationViewModel.dialogState.collectAsStateWithLifecycle()
    val formativeFieldJson = backStackEntry.arguments?.getString("formativeField")

    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        registerEvaluationViewModel.getListStudent()
        val formativeField: FormativeFieldDomainPar? = if (formativeFieldJson.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(formativeFieldJson, FormativeFieldDomainPar::class.java)
        }

        registerEvaluationViewModel.updateFormativeField(formativeField)
    }

    // Consumir eventos de navegación en lugar de observar estados
    LaunchedEffect(Unit) {
        registerEvaluationViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateBack -> navController.popBackStack()
                else -> { /* Otros eventos se manejan en otros lugares */ }
            }
        }
    }

    LaunchedEffect(uiState.controlToast) {
        if (uiState.controlToast.showToast) sharedViewModel.modifyShowToast(uiState.controlToast)
        registerEvaluationViewModel.modifyShowToast(false)
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        val (header, body, body2, column, action) = createRefs()

        Column(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            HeaderRegisterAssignment(
                uiState = uiState,
                navController = navController
            )
        }

        Column(
            modifier = Modifier.constrainAs(body) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            BodyRegisterAssignment(
                dataState = dataState,
                dialogState = dialogState,
                onNameJobChanged = { registerEvaluationViewModel.onNameChanged(it) },
                showDialog = {
                    registerEvaluationViewModel.updateDates()
                    showDialog.value = true
                }
            )
        }

        Column(
            modifier = Modifier.constrainAs(body2) {
                top.linkTo(body.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            Body2RegisterAssignment(
                dataState = dataState,
                onNameAssignmentChanged = { registerEvaluationViewModel.onNameAssignmentChanged(it) }
            )
        }

        Column(
            modifier = Modifier.constrainAs(column) {
                top.linkTo(body2.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(action.top)
                height = Dimension.fillToConstraints
            }) {
            ColumnRegisterScore(
                dataState = dataState,
                onScoreChange = { registerEvaluationViewModel.onScoreChange(it) }
            )
        }

        Column(
            modifier = Modifier.constrainAs(action) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            ActionRegisterAssignment(
                onClick = { registerEvaluationViewModel.validateFields() }
            )
        }

    }

    if (showDialog.value) {
        DateSimplePickerDialog(
            showDialog = true,
            dialogState = dialogState,
            onDismiss = { showDialog.value = false },
            onDateSelected = { registerEvaluationViewModel.onDateChanged(it.toString()) }
        )
    }
    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

/**
 * The header of the Assignment Registration screen.
 *
 * @param uiState The UI state for the screen.
 * @param navController The navigation controller.
 */
@Composable
private fun HeaderRegisterAssignment(
    uiState: RegisterEvaluationUiState,
    navController: NavHostController,
) {
    ComponentHeaderBackWithout(
        title = uiState.formativeField?.name ?: "Desconocido"
    ) { navController.popBackStack() }
}

/**
 * The body of the Assignment Registration screen.
 *
 * @param dataState The data state for the screen.
 * @param dialogState The state of the dialog.
 * @param onNameJobChanged A lambda to be invoked when the job name changes.
 * @param showDialog A lambda to be invoked to show the dialog.
 */
@Composable
fun BodyRegisterAssignment(
    dataState: RegisterEvaluationUiData,
    dialogState: ModelCustomCalendar,
    onNameJobChanged: (ModelStateOutFieldText) -> Unit,
    showDialog: () -> Unit,
) {
    TextFieldGeneric(
        modelText = dataState.nameJob,
        enable = true,
        label = stringResource(id = R.string.form_assignment_name),
        regex = COMPLEX_TEXT,
        onBoxChanged= { onNameJobChanged(it)}
    )

    TextFieldCalendar(
        value = dialogState.date,
        enable = true,
        label = stringResource(id = R.string.form_assignment_date)
    ) { showDialog() }
}

/**
 * The second part of the body of the Assignment Registration screen.
 *
 * @param dataState The data state for the screen.
 * @param onNameAssignmentChanged A lambda to be invoked when the assignment name changes.
 */
@Composable
private fun Body2RegisterAssignment(
    dataState: RegisterEvaluationUiData,
    onNameAssignmentChanged: (ModelCustomSpinner) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            CustomSpace(dimensionResource(R.dimen.margin_outer))
            TextBody(stringResource(R.string.assignment_type_evaluate))
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            DropdownTextField(
                options = dataState.listOptions!!,
                selectedOption = dataState.nameAssignment,
                read = false,
                label = stringResource(id = R.string.form_assignment_type),
                onOptionSelected = { onNameAssignmentChanged(it) }
            )
        }
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))
}

/**
 * The column for registering the score.
 *
 * @param dataState The data state for the screen.
 * @param onScoreChange A lambda to be invoked when the score changes.
 */
@Composable
private fun ColumnRegisterScore(
    dataState: RegisterEvaluationUiData,
    onScoreChange: (Pair<String, String>) -> Unit,
) {
    EvaluationStudentList(
        items = dataState.studentListUI,
        onScoreChange = { onScoreChange(it) },
    )
}

/**
 * The action button for the Assignment Registration screen.
 *
 * @param onClick A lambda to be invoked when the action button is clicked.
 */
@Composable
private fun ActionRegisterAssignment(
    onClick: () -> Unit,
) {
    CustomSpace(dimensionResource(R.dimen.margin_divided))
    ButtonAction(
        containerColor = colorAction,
        text = stringResource(R.string.save),
        onActionClick = { onClick() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}


/**
 * The action button for the Assignment Registration screen.
 *
 * @param onClick A lambda to be invoked when the action button is clicked.
 */
@Preview(showBackground = true)
@Composable
private fun RegisterEvaluationPreview(){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        val (header, body, body2, column, action) = createRefs()

        Column(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            HeaderRegisterAssignment(
                uiState = RegisterEvaluationUiState(),
                navController = NavHostController(context = LocalContext.current)
            )
        }

        Column(
            modifier = Modifier.constrainAs(body) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            BodyRegisterAssignment(
                dataState = RegisterEvaluationUiData(),
                dialogState = ModelCustomCalendar(),
                onNameJobChanged = {  },
                showDialog = { }
            )
        }

        Column(
            modifier = Modifier.constrainAs(body2) {
                top.linkTo(body.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            Body2RegisterAssignment(
                dataState = RegisterEvaluationUiData(),
                onNameAssignmentChanged = { }
            )
        }

        Column(
            modifier = Modifier.constrainAs(column) {
                top.linkTo(body2.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(action.top)
                height = Dimension.fillToConstraints
            }) {
            ColumnRegisterScore(
                dataState = RegisterEvaluationUiData(),
                onScoreChange = { }
            )
        }

        Column(
            modifier = Modifier.constrainAs(action) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            ActionRegisterAssignment(
                onClick = {  }
            )
        }

    }
}