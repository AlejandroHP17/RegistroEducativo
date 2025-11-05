package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.mx.liftechnology.core.util.logInfo
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterStudentCallbacksUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterStudentStateUI
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextAllCaps
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextCapitalLetterGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextNumeric
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonPair
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.DateSimplePickerDialog
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import org.koin.androidx.compose.koinViewModel

/**
 * The Student Registration screen.
 *
 * @param navController The navigation controller.
 * @param backStackEntry The back stack entry for this screen.
 * @param sharedViewModel The shared ViewModel.
 * @param registerStudentViewModel The ViewModel for this screen.
 */
@Composable
fun RegisterStudentScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    sharedViewModel: SharedViewModel,
    registerStudentViewModel: RegisterStudentViewModel = koinViewModel(),
) {
    val uiState by registerStudentViewModel.uiState.collectAsStateWithLifecycle()
    val studentJson = backStackEntry.arguments?.getString("student")

    var showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

        val student: ModelStudentDomain? = if (studentJson.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(studentJson, ModelStudentDomain::class.java)
        }
        student?.let { registerStudentViewModel.getArguments(it) }
    }

    LaunchedEffect(uiState.uiState) {
        if (uiState.uiState == ModelStateUIEnum.SUCCESS) navController.popBackStack()
    }

    LaunchedEffect(uiState.controlToast) {
        if (uiState.controlToast.showToast) sharedViewModel.modifyShowToast(uiState.controlToast)
        registerStudentViewModel.modifyShowToast(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    )
    {
        logInfo("Screen register student")
        HeaderRegisterStudent(navController = navController)

        BodyRegisterStudent(
            uiState = uiState,
            callbacks = ModelRegisterStudentCallbacksUI(
                onNameChanged = { registerStudentViewModel.onNameChanged(it) },
                onLastNameChanged = { registerStudentViewModel.onLastNameChanged(it) },
                onSecondLastNameChanged = { registerStudentViewModel.onSecondLastNameChanged(it) },
                onCurpChanged = { registerStudentViewModel.onCurpChanged(it) },
                onPhoneNumberChanged = { registerStudentViewModel.onPhoneNumberChanged(it) },
                onBirthdayChanged = {
                    showDialog.value = true
                },
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        ActionRegisterStudent(
            uiState = uiState,
            validateFieldsCompose = { registerStudentViewModel.validateFieldsCompose() },
            onRecord = { registerStudentViewModel.change() })
    }

    if (showDialog.value) {
        DateSimplePickerDialog(
            showDialog = true,
            dialogState = null,
            onDismiss = { showDialog.value = false },
            onDateSelected = { registerStudentViewModel.onBirthdayChanged(it.toString()) }
        )
    }

    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)

}

/**
 * The header of the Student Registration screen.
 *
 * @param navController The navigation controller.
 */
@Composable
private fun HeaderRegisterStudent(navController: NavHostController) {
    ComponentHeaderBack(
        title = stringResource(R.string.register_student_name),
        body = stringResource(R.string.register_student_name_description)
    ) { navController.popBackStack() }
}

/**
 * The body of the Student Registration screen.
 *
 * @param uiState The UI state for the screen.
 * @param callbacks The callbacks for the input fields.
 */
@Composable
private fun BodyRegisterStudent(
    uiState: ModelRegisterStudentStateUI,
    callbacks: ModelRegisterStudentCallbacksUI,
) {
    BoxEditTextCapitalLetterGeneric(
        value = uiState.name,
        enable = true,
        label = stringResource(id = R.string.form_student_name),
        onBoxChanged = { callbacks.onNameChanged(it) }
    )

    BoxEditTextCapitalLetterGeneric(
        value = uiState.lastName,
        enable = true,
        label = stringResource(id = R.string.form_student_last_name),
        onBoxChanged = { callbacks.onLastNameChanged(it) }
    )

    BoxEditTextCapitalLetterGeneric(
        value = uiState.secondLastName,
        enable = true,
        label = stringResource(id = R.string.form_student_second_last_name),
        onBoxChanged = { callbacks.onSecondLastNameChanged(it) }
    )

    BoxEditTextAllCaps(
        value = uiState.curp,
        enable = true,
        label = stringResource(id = R.string.form_student_curp),
        onBoxChanged = { callbacks.onCurpChanged(it) }
    )

    BoxEditTextCalendar(
        value = uiState.birthday,
        enable = false,
        label = stringResource(id = R.string.form_student_birthday),
        onBoxChanged = { callbacks.onBirthdayChanged() }
    )

    BoxEditTextNumeric(
        value = uiState.phoneNumber,
        enable = true,
        label = stringResource(id = R.string.form_student_phone_number),
        maxNumberCharacter = 10,
        onBoxChanged = { callbacks.onPhoneNumberChanged(it) }
    )
}

/**
 * The action button of the Student Registration screen.
 *
 * @param uiState The UI state for the screen.
 * @param validateFieldsCompose A lambda to be invoked when the action button is clicked.
 * @param onRecord A lambda to be invoked when the record button is clicked.
 */
@Composable
private fun ActionRegisterStudent(
    uiState: ModelRegisterStudentStateUI,
    validateFieldsCompose: () -> Unit,
    onRecord: () -> Unit,
) {
    ButtonPair(
        actionColor = colorAction,
        recordColor = uiState.buttonColor,
        text = stringResource(R.string.add_button),
        onActionClick = { validateFieldsCompose() },
        onRecordClick = { onRecord() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}