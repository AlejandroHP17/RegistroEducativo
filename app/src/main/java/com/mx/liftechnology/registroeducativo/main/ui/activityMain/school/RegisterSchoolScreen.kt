package com.mx.liftechnology.registroeducativo.main.ui.activityMain.school

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.navigation.NavHostController
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterSchoolUIState
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextAllCaps
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonPair
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.SpinnerOutlinedTextField
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterSchoolScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    registerSchoolViewModel: RegisterSchoolViewModel = koinViewModel(),
) {
    val uiState by registerSchoolViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.uiState) {
        if (uiState.uiState == ModelStateUIEnum.SUCCESS)navController.navigate(MainRoutes.Menu.withReload(true)) {
            popUpTo(MainRoutes.Menu.route) { inclusive = true }
        }
    }

    LaunchedEffect (uiState.controlToast) {
        if (uiState.controlToast.showToast) sharedViewModel.modifyShowToast( uiState.controlToast)
        registerSchoolViewModel.modifyShowToast(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        logs("Screen register school")
        HeaderRegisterSchool(navController = navController)

        BodyRegisterSchool(
            uiState = uiState,
            onCctChanged =  { registerSchoolViewModel.onCctChanged(it) }
        )

        BodyDoubleRegisterSchool(
            uiState = uiState,
            onCycleChanged = { registerSchoolViewModel.onCycleChanged(it) },
            onGradeChanged = { registerSchoolViewModel.onGradeChanged(it) },
            onGroupChanged = { registerSchoolViewModel.onGroupChanged(it) }
        )

        Spacer(modifier = Modifier.weight(1f))

        ActionRegisterSchool(
            uiState = uiState,
            validateFieldsCompose = { registerSchoolViewModel.validateFields() },
            onRecord = {registerSchoolViewModel.change()})
    }

    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

@Composable
private fun HeaderRegisterSchool(navController: NavHostController) {
    ComponentHeaderBack(
        title = stringResource(R.string.register_school, "Profesor"),
        body = stringResource(R.string.register_school_description)
    ) { navController.popBackStack() }
}

@Composable
private fun BodyRegisterSchool(
    uiState: ModelRegisterSchoolUIState,
    onCctChanged: (String) -> Unit,
) {
    BoxEditTextAllCaps (
        value = uiState.cct,
        enable = true,
        label = stringResource(id = R.string.form_school_cct),
        onBoxChanged = { onCctChanged(it) }
    )

    BoxEditTextGeneric(
        value = uiState.schoolName,
        enable = false,
        label = stringResource(id = R.string.form_school_name),
    ) {}

    BoxEditTextGeneric(
        value = uiState.shift,
        enable = false,
        label = stringResource(id = R.string.form_school_shift),
    ) {}
}

@Composable
private fun BodyDoubleRegisterSchool(
    uiState: ModelRegisterSchoolUIState,
    onCycleChanged: (String) -> Unit,
    onGradeChanged: (String) -> Unit,
    onGroupChanged: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Box(modifier = Modifier.weight(1f)) {
            BoxEditTextGeneric(
                value = uiState.type,
                enable = false,
                label = stringResource(id = R.string.form_school_type),
            ) {}
        }

        Box(modifier = Modifier.weight(1f)) {
            SpinnerOutlinedTextField(
                options = uiState.spinner?.cycle ?: emptyList(),
                selectedOption = uiState.cycle,
                read = uiState.read,
                label = stringResource(id = R.string.form_school_term),
                onOptionSelected = { onCycleChanged(it) }
            )
        }
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Box(modifier = Modifier.weight(1f)) {
            SpinnerOutlinedTextField(
                options = uiState.spinner?.grade ?: emptyList(),
                selectedOption = uiState.grade,
                read = uiState.read,
                label = stringResource(id = R.string.form_school_grade),
                onOptionSelected = { onGradeChanged(it) }
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            SpinnerOutlinedTextField(
                options = uiState.spinner?.group ?: emptyList(),
                selectedOption = uiState.group,
                read = uiState.read,
                label = stringResource(id = R.string.form_school_group),
                onOptionSelected = { onGroupChanged(it) }
            )
        }
    }
}

@Composable
private fun ActionRegisterSchool(
    uiState: ModelRegisterSchoolUIState,
    validateFieldsCompose: () -> Unit,
    onRecord: () -> Unit,
) {
    ButtonPair(
        actionColor = color_action,
        recordColor = uiState.buttonColor,
        text = stringResource(R.string.add_button),
        onActionClick = { validateFieldsCompose() },
        onRecordClick = {onRecord()}
    )

    CustomSpace(dimensionResource(R.dimen.margin_divided))
}