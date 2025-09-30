package com.mx.liftechnology.registroeducativo.main.ui.flowMain.school

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterSchoolUICallbacks
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterSchoolUISemiAutomaticData
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterSchoolUIState
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextAllCaps
import com.mx.liftechnology.registroeducativo.main.ui.components.BoxEditTextSimpleGeneric
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonPair
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.SpinnerOutlinedTextField
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import com.mx.liftechnology.registroeducativo.main.util.navigation.MainRoutes
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterSchoolScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    registerSchoolViewModel: RegisterSchoolViewModel = koinViewModel(),
) {
    val uiState by registerSchoolViewModel.uiState.collectAsStateWithLifecycle()
    val uiSemiAutomaticData by registerSchoolViewModel.uiSemiAutomaticData.collectAsStateWithLifecycle()
    val cct by registerSchoolViewModel.cct.collectAsStateWithLifecycle()
    val grade by registerSchoolViewModel.grade.collectAsStateWithLifecycle()
    val group by registerSchoolViewModel.group.collectAsStateWithLifecycle()
    val cycle by registerSchoolViewModel.cycle.collectAsStateWithLifecycle()

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
        HeaderRegisterSchool(navController = navController)

        BodyRegisterSchool(
            cct = cct,
            uiAutomatic = uiSemiAutomaticData,
            onCctChanged =  { registerSchoolViewModel.onCctChanged(it) }
        )

        BodyDoubleRegisterSchool(
            semiAutomatic = uiSemiAutomaticData,
            cycle = cycle,
            grade = grade,
            group = group,
            callbacks = ModelRegisterSchoolUICallbacks(
                onCycleChanged = {registerSchoolViewModel.onCycleChanged(it)},
                onGradeChanged = {registerSchoolViewModel.onGradeChanged(it)},
                onGroupChanged = { registerSchoolViewModel.onGroupChanged(it) }
            )
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
    cct: ModelStateOutFieldText,
    uiAutomatic: ModelRegisterSchoolUISemiAutomaticData,
    onCctChanged: (String) -> Unit,
) {
    BoxEditTextAllCaps (
        value = cct,
        enable = true,
        label = stringResource(id = R.string.form_school_cct),
        onBoxChanged = { onCctChanged(it) }
    )

    BoxEditTextSimpleGeneric(
        value = uiAutomatic.schoolName,
        enable = false,
        label = stringResource(id = R.string.form_school_name),
    ) {}

    BoxEditTextSimpleGeneric(
        value = uiAutomatic.shift,
        enable = false,
        label = stringResource(id = R.string.form_school_shift),
    ) {}
}

@Composable
private fun BodyDoubleRegisterSchool(
    semiAutomatic: ModelRegisterSchoolUISemiAutomaticData,
    cycle: ModelStateOutFieldText,
    grade: ModelStateOutFieldText,
    group: ModelStateOutFieldText,
    callbacks: ModelRegisterSchoolUICallbacks
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Box(modifier = Modifier.weight(1f)) {
            BoxEditTextSimpleGeneric(
                value = semiAutomatic.type,
                enable = false,
                label = stringResource(id = R.string.form_school_type),
            ) {}
        }

        Box(modifier = Modifier.weight(1f)) {
            SpinnerOutlinedTextField(
                options = semiAutomatic.spinner?.cycle ?: emptyList(),
                selectedOption = cycle,
                read = semiAutomatic.read,
                label = stringResource(id = R.string.form_school_term),
                onOptionSelected = { callbacks.onCycleChanged(it.value.toString()) }
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
                options = semiAutomatic.spinner?.grade ?: emptyList(),
                selectedOption = grade,
                read = semiAutomatic.read,
                label = stringResource(id = R.string.form_school_grade),
                onOptionSelected = { callbacks.onGradeChanged(it.value.toString()) }
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            SpinnerOutlinedTextField(
                options = semiAutomatic.spinner?.group ?: emptyList(),
                selectedOption = group,
                read = semiAutomatic.read,
                label = stringResource(id = R.string.form_school_group),
                onOptionSelected = { callbacks.onGroupChanged(it.value.toString()) }
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
        actionColor = colorAction,
        recordColor = uiState.buttonColor,
        text = stringResource(R.string.add_button),
        onActionClick = { validateFieldsCompose() },
        onRecordClick = {onRecord()}
    )

    CustomSpace(dimensionResource(R.dimen.margin_divided))
}