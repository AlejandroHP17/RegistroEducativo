package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.register

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterStudentUIState
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextAllCaps
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextNumeric
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonPair
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar


@Composable
fun RegisterStudentScreen(
    navController: NavHostController,
    backStackEntry : NavBackStackEntry,
    registerStudentViewModel: RegisterStudentViewModel = koinViewModel(),
){
    val uiState by registerStudentViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val studentJson = backStackEntry.arguments?.getString("student")


    LaunchedEffect(Unit) {

        val student: ModelStudentDomain? = if (studentJson.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(studentJson, ModelStudentDomain::class.java)
        }
        student?.let { registerStudentViewModel.getArguments(it) }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.popBackStack()
        }
    }

    // Estado para controlar la fecha seleccionada
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$year-${month + 1}-$dayOfMonth"
                registerStudentViewModel.onChangeBirthday(selectedDate) // Guardar la fecha
            },
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(id = R.dimen.margin_outer))
        )
        {

            HeaderRegisterStudent(navController = navController)

            BodyRegisterStudent(
                uiState = uiState,
                onChangeName = {registerStudentViewModel.onChangeName(it)},
                onChangeLastName = {registerStudentViewModel.onChangeLastName(it)},
                onChangeSecondLastName = {registerStudentViewModel.onChangeSecondLastName(it)}
            )

            Body2RegisterStudent(
                uiState = uiState,
                onChangeCurp = {registerStudentViewModel.onChangeCurp(it)},
                datePickerDialog = datePickerDialog,
                onChangePhoneNUmber = {registerStudentViewModel.onChangePhoneNUmber(it)}
            )

            Spacer(modifier = Modifier.weight(1f))

            ActionRegisterStudent { registerStudentViewModel.validateFieldsCompose() }
        }

        LoadingAnimation(uiState.isLoading)
    }
}


@Composable
private fun HeaderRegisterStudent(navController: NavHostController) {
    ComponentHeaderBack(
        title = stringResource(R.string.register_student_name),
        body = stringResource(R.string.register_student_name_description)
    ) {  navController.popBackStack() }
}

@Composable
private fun BodyRegisterStudent(
    uiState: ModelRegisterStudentUIState,
    onChangeName:(String) -> Unit,
    onChangeLastName:(String) -> Unit,
    onChangeSecondLastName:(String) -> Unit
) {
    BoxEditTextGeneric(
        value = uiState.name,
        enable = true,
        label = stringResource(id = R.string.form_student_name),
        error = uiState.isErrorName
    ){ onChangeName(it)}

    BoxEditTextGeneric(
        value = uiState.lastName,
        enable = true,
        label = stringResource(id = R.string.form_student_last_name),
        error = uiState.isErrorLastName
    ){ onChangeLastName(it)}

    BoxEditTextGeneric(
        value = uiState.secondLastName,
        enable = true,
        label = stringResource(id = R.string.form_student_second_last_name),
        error = uiState.isErrorSecondLastName
    ){ onChangeSecondLastName(it)}
}

@Composable
private fun Body2RegisterStudent(
    uiState: ModelRegisterStudentUIState,
    onChangeCurp:(String) -> Unit,
    datePickerDialog: DatePickerDialog,
    onChangePhoneNUmber:(String) -> Unit
) {
    BoxEditTextAllCaps(
        value = uiState.curp,
        enable = true,
        label = stringResource(id = R.string.form_student_curp),
        error = uiState.isErrorCurp
    ){ onChangeCurp(it)}

    BoxEditTextCalendar(
        value = uiState.birthday,
        enable = false,
        label = stringResource(id = R.string.form_student_birthday),
        error = uiState.isErrorBirthday
    ){ datePickerDialog.show()}

    BoxEditTextNumeric(
        value = uiState.phoneNumber,
        enable = true,
        label = stringResource(id = R.string.form_student_phone_number),
        error = uiState.isErrorPhoneNumber
    ){ onChangePhoneNUmber(it)}
}


@Composable
private fun ActionRegisterStudent(
    validateFieldsCompose:() -> Unit
) {
    ButtonPair(
        containerColor = color_action,
        text = stringResource(R.string.add_button),
        onActionClick = { validateFieldsCompose() },
        onRecordClick = {}
    )
}