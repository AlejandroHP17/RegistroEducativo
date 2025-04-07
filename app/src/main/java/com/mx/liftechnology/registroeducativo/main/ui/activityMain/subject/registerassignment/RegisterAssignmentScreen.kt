package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.registerassignment

import android.app.DatePickerDialog
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterAssignmentUIState
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBackWithout
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.EvaluationStudentList
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.SpinnerOutlinedTextField
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Composable
fun RegisterAssignmentScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    registerAssignmentViewModel: RegisterAssignmentViewModel = koinViewModel(),
) {

    val uiState by registerAssignmentViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val subjectJson = backStackEntry.arguments?.getString("subject")

    LaunchedEffect(Unit) {
        registerAssignmentViewModel.getListStudent()
        val subject: ModelFormatSubjectDomain? = if (subjectJson.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(subjectJson, ModelFormatSubjectDomain::class.java)
        }

        registerAssignmentViewModel.updateSubject(subject)

    }

    // Estado para controlar la fecha seleccionada
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$year-${month + 1}-$dayOfMonth"
                registerAssignmentViewModel.onChangeDate(selectedDate) // Guardar la fecha
            },
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        )
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
                uiState = uiState,
                onNameJobChanged = { registerAssignmentViewModel.onChangeName(it) },
                datePickerDialog = datePickerDialog,
            )
        }

        Column(
            modifier = Modifier.constrainAs(body2) {
                top.linkTo(body.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            Body2RegisterAssignment(
                uiState = uiState,
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
                uiState = uiState,
                onScoreChange = { registerAssignmentViewModel.onScoreChange(it) }
            )
        }

        Column(
            modifier = Modifier.constrainAs(action) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            ActionRegisterAssignment()
        }

    }
    LoadingAnimation(uiState.isLoading)
}

@Composable
private fun HeaderRegisterAssignment(
    uiState: ModelRegisterAssignmentUIState,
    navController: NavHostController,
) {
    ComponentHeaderBackWithout(
        title = uiState.subject?.name ?: "Desconocido"
    ) { navController.popBackStack() }
}

@Composable
fun BodyRegisterAssignment(
    uiState: ModelRegisterAssignmentUIState,
    onNameJobChanged: (String) -> Unit,
    datePickerDialog: DatePickerDialog,
) {
    BoxEditTextGeneric(
        value = uiState.nameJob,
        enable = true,
        label = stringResource(id = R.string.form_assignment_name),
    ) { onNameJobChanged(it) }


    BoxEditTextCalendar(
        value = uiState.date,
        enable = true,
        label = stringResource(id = R.string.form_assignment_date)
    ) { datePickerDialog.show() }
}

@Composable
private fun Body2RegisterAssignment(
    uiState: ModelRegisterAssignmentUIState,
    onNameAssignmentChanged: (String) -> Unit,
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
                options = uiState.listOptions,
                selectedOption = uiState.nameAssignment,
                read = false,
                label = stringResource(id = R.string.assignment_type),
                onOptionSelected = { onNameAssignmentChanged(it) }
            )
        }
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))
}

@Composable
private fun ColumnRegisterScore(
    uiState: ModelRegisterAssignmentUIState,
    onScoreChange: (Pair<String, String>) -> Unit,
) {
    EvaluationStudentList(
        items = uiState.studentListUI,
        onScoreChange = { onScoreChange(it) },
    )
}

@Composable
private fun ActionRegisterAssignment(
) {
    CustomSpace(dimensionResource(R.dimen.margin_divided))
    ButtonAction(
        containerColor = color_action,
        text = stringResource(R.string.save),
        onActionClick = { }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}