package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.registerassignment

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterAssignmentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterAssignmentUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCalendar
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomSpinner
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextComplexGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBackWithout
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.DateSimplePickerDialog
import com.mx.liftechnology.registroeducativo.main.ui.components.EvaluationStudentList
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.SpinnerOutlinedTextField
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import org.koin.androidx.compose.koinViewModel

/**
 * The Assignment Registration screen.
 *
 * @param navController The navigation controller.
 * @param backStackEntry The back stack entry for this screen.
 * @param registerAssignmentViewModel The ViewModel for this screen.
 * @param sharedViewModel The shared ViewModel.
 */
@Composable
fun RegisterAssignmentScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    registerAssignmentViewModel: RegisterAssignmentViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel,
) {

    val uiState by registerAssignmentViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by registerAssignmentViewModel.dataState.collectAsStateWithLifecycle()
    val dialogState by registerAssignmentViewModel.dialogState.collectAsStateWithLifecycle()
    val subjectJson = backStackEntry.arguments?.getString("subject")

    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        registerAssignmentViewModel.getListStudent()
        val subject: ModelFormatSubjectDomain? = if (subjectJson.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(subjectJson, ModelFormatSubjectDomain::class.java)
        }

        registerAssignmentViewModel.updateSubject(subject)
    }

    LaunchedEffect(uiState.uiState) {
        if (uiState.uiState == ModelStateUIEnum.SUCCESS) navController.popBackStack()
    }

    LaunchedEffect(uiState.controlToast) {
        if (uiState.controlToast.showToast) sharedViewModel.modifyShowToast(uiState.controlToast)
        registerAssignmentViewModel.modifyShowToast(false)
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        logs("Screen list student assignment")
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
                onNameJobChanged = { registerAssignmentViewModel.onChangeName(it) },
                showDialog = {
                    registerAssignmentViewModel.updateDates()
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
                onNameAssignmentChanged = { registerAssignmentViewModel.onNameAssignmentChanged(it) }
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
                onScoreChange = { registerAssignmentViewModel.onScoreChange(it) }
            )
        }

        Column(
            modifier = Modifier.constrainAs(action) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            ActionRegisterAssignment(
                onClick = { registerAssignmentViewModel.validateFields() }
            )
        }

    }

    if (showDialog.value) {
        DateSimplePickerDialog(
            showDialog = true,
            dialogState = dialogState,
            onDismiss = { showDialog.value = false },
            onDateSelected = { registerAssignmentViewModel.onChangeDate(it.toString()) }
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
    uiState: ModelRegisterAssignmentUiState,
    navController: NavHostController,
) {
    ComponentHeaderBackWithout(
        title = uiState.subject?.name ?: "Desconocido"
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
    dataState: ModelRegisterAssignmentDataState,
    dialogState: ModelCustomCalendar,
    onNameJobChanged: (String) -> Unit,
    showDialog: () -> Unit,
) {
    BoxEditTextComplexGeneric(
        value = dataState.nameJob,
        enable = true,
        label = stringResource(id = R.string.form_assignment_name),
    ) { onNameJobChanged(it) }

    BoxEditTextCalendar(
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
    dataState: ModelRegisterAssignmentDataState,
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
            SpinnerOutlinedTextField(
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
    dataState: ModelRegisterAssignmentDataState,
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