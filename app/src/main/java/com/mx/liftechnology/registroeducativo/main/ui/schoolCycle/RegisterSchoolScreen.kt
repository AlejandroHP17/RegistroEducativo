/**
 * @file Define la pantalla para el registro de una nueva escuela por parte de un profesor.
 * @author PelkiDev
 * @version 1.0.0
 */
package com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.school

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.event.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.schoolCycle.RegisterSchoolUiInputs
import com.mx.liftechnology.registroeducativo.main.model.schoolCycle.RegisterSchoolUiState
import com.mx.liftechnology.registroeducativo.main.model.schoolCycle.RegisterSchoolUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.schoolCycle.RegisterSchoolUiSemiAutomaticData
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldAllCaps
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldNumeric
import com.mx.liftechnology.registroeducativo.main.ui.components.form.TextFieldSet
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonPair
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.form.DropdownTextField
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import com.mx.liftechnology.registroeducativo.main.util.navigation.AppRoutes
import org.koin.androidx.compose.koinViewModel

/**
 * Pantalla para el registro de una escuela.
 * Este Composable maneja la lógica de la UI para que un profesor pueda registrar una escuela
 * introduciendo una CCT y seleccionando el grado, grupo y ciclo.
 *
 * @param navController El controlador de navegación para gestionar los desplazamientos.
 * @param sharedViewModel El ViewModel compartido para la comunicación entre pantallas (ej: mostrar toasts).
 * @param registerSchoolViewModel El ViewModel específico para esta pantalla.
 * @author PelkiDev
 * @version 1.0.0
 */
@Composable
fun RegisterSchoolScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    registerSchoolViewModel: RegisterSchoolViewModel = koinViewModel(),
) {
    val uiState by registerSchoolViewModel.uiState.collectAsStateWithLifecycle()
    val uiSemiAutomaticData by registerSchoolViewModel.uiSemiAutomaticData.collectAsStateWithLifecycle()
    val inputState by registerSchoolViewModel.inputState.collectAsStateWithLifecycle()

    // Consumir eventos de navegación en lugar de observar estados
    LaunchedEffect(Unit) {
        registerSchoolViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.route) {
                        popUpTo(AppRoutes.Main.MENU) { inclusive = true }
                    }
                }
                else -> { /* Otros eventos se manejan en otros lugares */ }
            }
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
            inputState = inputState,
            uiAutomatic = uiSemiAutomaticData,
            onCctChanged =  { registerSchoolViewModel.onCctChanged(it) },
            onLabelCycleChanged = { registerSchoolViewModel.onLabelCycleChanged(it) }
        )

        BodyDoubleRegisterSchool(
            semiAutomatic = uiSemiAutomaticData,
            inputState = inputState,
            callbacks = RegisterSchoolUiCallbacks(
                onTypeChanged = {registerSchoolViewModel.onTypeChanged(it)},
                onCycleChanged = {registerSchoolViewModel.onCycleChanged(it)},
                onGradeChanged = {registerSchoolViewModel.onGradeChanged(it)},
                onGroupChanged = {registerSchoolViewModel.onGroupChanged(it) }
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        ActionRegisterSchool(
            uiState = uiState,
            validateFieldsCompose = { registerSchoolViewModel.validateFields() },
            onRecord = {registerSchoolViewModel.change()})
    }

    LoadingAnimation(uiState.uiState == EnumUi.LOADING)
}

/**
 * Encabezado de la pantalla de Registro de Escuela.
 *
 * @param navController El controlador de navegación para manejar la acción de retroceso.
 * @author PelkiDev
 * @version 1.0.0
 */
@Composable
private fun HeaderRegisterSchool(navController: NavHostController) {
    ComponentHeaderBack(
        title = stringResource(R.string.register_school, "Profesor"),
        body = stringResource(R.string.register_school_description)
    ) { navController.popBackStack() }
}

/**
 * Cuerpo principal de la pantalla de Registro de Escuela.
 * Contiene los campos de texto para la CCT y los datos autocompletados de la escuela.
 *
 * @param inputState El estado de los campos de entrada controlados por el usuario (ej: CCT).
 * @param uiAutomatic Los datos que se rellenan de forma semi-automática tras validar la CCT.
 * @param onCctChanged Lambda que se invoca cuando el valor del campo CCT cambia.
 * @author PelkiDev
 * @version 1.0.0
 */
@Composable
private fun BodyRegisterSchool(
    inputState: RegisterSchoolUiInputs,
    uiAutomatic: RegisterSchoolUiSemiAutomaticData,
    onCctChanged: (ModelStateOutFieldText) -> Unit,
    onLabelCycleChanged: (ModelStateOutFieldText) -> Unit,
) {
    TextFieldAllCaps (
        modelText = inputState.cct,
        enable = true,
        label = stringResource(id = R.string.form_school_cct),
        onBoxChanged = { onCctChanged(it) }
    )

    TextFieldSet(
        modelText = uiAutomatic.schoolName,
        label = stringResource(id = R.string.form_school_name),
    )

    TextFieldSet(
        modelText = uiAutomatic.shiftName,
        label = stringResource(id = R.string.form_school_shift),
    )

    TextFieldNumeric(
        modelText = inputState.labelCycle,
        enable = true,
        label = stringResource(id = R.string.form_school_label_cycle),
        maxNumberCharacter = 15,
        onBoxChanged = { onLabelCycleChanged(it) }
    )
}

/**
 * Cuerpo secundario de la pantalla con campos dobles (spinners).
 * Contiene los campos para seleccionar tipo, ciclo, grado y grupo.
 *
 * @param semiAutomatic Los datos semi-automáticos, incluyendo las listas para los spinners.
 * @param inputState El estado de los campos de entrada seleccionados por el usuario (ciclo, grado, grupo).
 * @param callbacks Los callbacks para manejar los cambios en los spinners.
 * @author PelkiDev
 * @version 1.0.0
 */
@Composable
private fun BodyDoubleRegisterSchool(
    semiAutomatic: RegisterSchoolUiSemiAutomaticData,
    inputState: RegisterSchoolUiInputs,
    callbacks: RegisterSchoolUiCallbacks
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Box(modifier = Modifier.weight(1f)) {
            DropdownTextField(
                options = semiAutomatic.spinner?.type ?: emptyList(),
                selectedOption = inputState.type,
                read = semiAutomatic.read,
                label = stringResource(id = R.string.form_school_type),
                onOptionSelected = { callbacks.onTypeChanged(it) }
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            DropdownTextField(
                options = semiAutomatic.spinner?.cycle ?: emptyList(),
                selectedOption = inputState.cycle,
                read = semiAutomatic.read,
                label = stringResource(id = R.string.form_school_term),
                onOptionSelected = { callbacks.onCycleChanged(it) }
            )
        }
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Box(modifier = Modifier.weight(1f)) {
            DropdownTextField(
                options = semiAutomatic.spinner?.grade ?: emptyList(),
                selectedOption = inputState.grade,
                read = semiAutomatic.read,
                label = stringResource(id = R.string.form_school_grade),
                onOptionSelected = { callbacks.onGradeChanged(it) }
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            DropdownTextField(
                options = semiAutomatic.spinner?.group ?: emptyList(),
                selectedOption = inputState.group,
                read = semiAutomatic.read,
                label = stringResource(id = R.string.form_school_group),
                onOptionSelected = { callbacks.onGroupChanged(it) }
            )
        }
    }
}

/**
 * Botones de acción para la pantalla de Registro de Escuela.
 *
 * @param uiState El estado de la UI, usado para determinar el color de uno de los botones.
 * @param validateFieldsCompose Lambda que se invoca para validar los campos antes de la acción principal.
 * @param onRecord Lambda que se invoca al pulsar el botón de grabación/acción.
 * @author Pelkidev
 * @version 1.0.0
 */
@Composable
private fun ActionRegisterSchool(
    uiState: RegisterSchoolUiState,
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

@Preview(showBackground = true)
@Composable
private fun RegisterSchoolPreview(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        HeaderRegisterSchool(NavHostController(context = LocalContext.current))

        BodyRegisterSchool(
            inputState = RegisterSchoolUiInputs(),
            uiAutomatic = RegisterSchoolUiSemiAutomaticData(),
            onCctChanged =  { },
            onLabelCycleChanged = {}
        )

        BodyDoubleRegisterSchool(
            semiAutomatic = RegisterSchoolUiSemiAutomaticData(),
            inputState = RegisterSchoolUiInputs(),
            callbacks = RegisterSchoolUiCallbacks(
                onTypeChanged = {},
                onCycleChanged = {},
                onGradeChanged = {},
                onGroupChanged = { }
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        ActionRegisterSchool(
            uiState = RegisterSchoolUiState(),
            validateFieldsCompose = { },
            onRecord = {})
    }
}