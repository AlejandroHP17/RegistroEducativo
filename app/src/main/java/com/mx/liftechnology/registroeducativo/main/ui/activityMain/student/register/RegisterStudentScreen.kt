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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonPair
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Preview(showBackground = true)
@Composable
fun PreviewRegisterStudentScreen() {
    val navController = rememberNavController()
    RegisterStudentScreen(navController)
}

@Composable
fun RegisterStudentScreen(
    navController: NavHostController,
    registerStudentViewModel: RegisterStudentViewModel = koinViewModel(),
){
    val uiState by registerStudentViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

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
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
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

            ComponentHeaderBack(
                title = stringResource(R.string.register_student_name),
                body = stringResource(R.string.register_student_name_description)
            ) {  navController.popBackStack() }

            BoxEditTextGeneric(
                value = uiState.name,
                enable = true,
                label = stringResource(id = R.string.form_student_name),
                error = uiState.isErrorName
            ){ registerStudentViewModel.onChangeName(it)}

            BoxEditTextGeneric(
                value = uiState.lastName,
                enable = true,
                label = stringResource(id = R.string.form_student_last_name),
                error = uiState.isErrorLastName
            ){ registerStudentViewModel.onChangeLastName(it)}

            BoxEditTextGeneric(
                value = uiState.secondLastName,
                enable = true,
                label = stringResource(id = R.string.form_student_second_last_name),
                error = uiState.isErrorSecondLastName
            ){ registerStudentViewModel.onChangeSecondLastName(it)}

            BoxEditTextGeneric(
                value = uiState.curp,
                enable = true,
                label = stringResource(id = R.string.form_student_curp),
                error = uiState.isErrorCurp
            ){ registerStudentViewModel.onChangeCurp(it)}

            BoxEditTextCalendar(
                value = uiState.birthday,
                enable = false,
                label = stringResource(id = R.string.form_student_birthday),
                error = uiState.isErrorBirthday
            ){ datePickerDialog.show()}

            BoxEditTextGeneric(
                value = uiState.phoneNumber,
                enable = true,
                label = stringResource(id = R.string.form_student_phone_number),
                error = uiState.isErrorPhoneNumber
            ){ registerStudentViewModel.onChangePhoneNUmber(it)}

            Spacer(modifier = Modifier.weight(1f))

            ButtonPair(
                containerColor = color_action,
                text = stringResource(R.string.add_button),
                onActionClick = { registerStudentViewModel.validateFieldsCompose() },
                onRecordClick = {}
            )
        }

        LoadingAnimation(uiState.isLoading)
    }
}