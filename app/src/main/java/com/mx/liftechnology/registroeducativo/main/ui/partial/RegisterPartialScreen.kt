package com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.partial

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.event.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.partial.RegisterPartialUiData
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.feedback.AlertDialogConfirm
import com.mx.liftechnology.registroeducativo.main.ui.components.form.DropdownTextField
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.RegisterPartialList
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

/**
 * The Partial Registration screen.
 *
 * @param navController The navigation controller.
 * @param registerPartialViewModel The ViewModel for this screen.
 * @param sharedViewModel The shared ViewModel.
 */
@Composable
fun RegisterPartialScreen(
    navController: NavHostController,
    registerPartialViewModel: RegisterPartialViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel
) {

    val uiState by registerPartialViewModel.uiState.collectAsStateWithLifecycle()
    val uiData by registerPartialViewModel.uiData.collectAsStateWithLifecycle()

    val showDialog = remember { mutableStateOf(false) }

    // Consumir eventos de navegación en lugar de observar estados
    LaunchedEffect(Unit) {
        registerPartialViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateBack -> navController.popBackStack()
                else -> { /* Otros eventos se manejan en otros lugares */ }
            }
        }
    }

    LaunchedEffect (uiState.controlToast) {
        if (uiState.controlToast.showToast) sharedViewModel.modifyShowToast( uiState.controlToast)
        registerPartialViewModel.modifyShowToast(false)
    }

    LaunchedEffect(Unit) {
        registerPartialViewModel.getListPartialCompose()
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
            }) { HeaderRegisterPartial(navigate =  {navController.popBackStack()}) }

        Column(
            modifier = Modifier.constrainAs(body) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            BodyRegisterPartial(
                uiData = uiData,
                onPartialChanged = { registerPartialViewModel.onPartialChanged(it.value.toString()) }
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
            if (uiData.numberPartials.valueText.isNotEmpty() && uiData.numberPartials.valueText.toInt() > 0) {
                ColumnRegisterPartial(
                    uiData = uiData,
                    onDateChange = { registerPartialViewModel.onDateChange(it) },
                )
            }
        }

        Column(
            modifier = Modifier.constrainAs(action) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) { ActionRegisterPartial (
            isAvailable = uiState.isAvailable,
            validateFieldsCompose = { showDialog.value = true }
        ) }
    }

    if (showDialog.value) {
        AlertDialogConfirm(
            itemSelectedReturn = {
                if(it) registerPartialViewModel.validateFieldsCompose()
            },
            dismiss = { showDialog.value = false }
        )
    }

    LoadingAnimation(uiState.uiState == ModelStateUIEnum.LOADING)
}

/**
 * The header of the Partial Registration screen.
 *
 * @param navigate A lambda to be invoked when the back button is clicked.
 */
@Composable
private fun HeaderRegisterPartial(
    navigate : () -> Unit
) {
    ComponentHeaderBack(
        title = stringResource(R.string.register_partial),
        body = stringResource(R.string.register_formative_field_name_description_2),
        onReturnClick = { navigate()})
}

/**
 * The body of the Partial Registration screen.
 *
 * @param uiData The data state for the screen.
 * @param onPartialChanged A lambda to be invoked when the number of partials changes.
 */
@Composable
private fun BodyRegisterPartial(
    uiData: RegisterPartialUiData,
    onPartialChanged: (ModelCustomSpinner) -> Unit,
) {
    CustomSpace(dimensionResource(R.dimen.margin_between))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            CustomSpace(dimensionResource(R.dimen.margin_outer))
            TextBody(stringResource(R.string.register_partial_number_period))
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            DropdownTextField(
                options = uiData.listOptions,
                selectedOption = uiData.numberPartials,
                read = uiData.read,
                label = stringResource(id = R.string.form_partial_period),
                onOptionSelected = { onPartialChanged(it) }
            )
        }
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))
}

/**
 * The column of the Partial Registration screen.
 *
 * @param uiData The data state for the screen.
 * @param onDateChange A lambda to be invoked when a date range changes.
 */
@Composable
private fun ColumnRegisterPartial(
    uiData: RegisterPartialUiData,
    onDateChange: (
        Pair<Pair<LocalDate?, LocalDate?>, Int>,
    ) -> Unit,
) {
    RegisterPartialList(
        items = uiData.listCalendar!!,
        isActive = !uiData.read,
        onDateChange = { onDateChange(it) })
}

/**
 * The action button of the Partial Registration screen.
 *
 * @param isAvailable Whether the button is enabled.
 * @param validateFieldsCompose A lambda to be invoked when the action button is clicked.
 */
@Composable
private fun ActionRegisterPartial(
    isAvailable: Boolean,
    validateFieldsCompose: () -> Unit,
) {
    CustomSpace(dimensionResource(R.dimen.margin_divided))
    ButtonAction(
        isAvailable = isAvailable,
        containerColor = colorAction,
        text = stringResource(R.string.add_button),
        onActionClick = { validateFieldsCompose() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}

@Preview(showBackground = true)
@Composable
private fun RegisterPartialPreview(){
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
            }) { HeaderRegisterPartial(navigate =  {}) }

        Column(
            modifier = Modifier.constrainAs(body) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            BodyRegisterPartial(
                uiData = RegisterPartialUiData(),
                onPartialChanged = { }
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

                ColumnRegisterPartial(
                    uiData = RegisterPartialUiData(),
                    onDateChange = { },
                )

        }

        Column(
            modifier = Modifier.constrainAs(action) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) { ActionRegisterPartial (
            isAvailable = true,
            validateFieldsCompose = {  }
        ) }
    }
}
