package com.mx.liftechnology.registroeducativo.main.ui.activityMain.partial

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
import androidx.navigation.NavHostController
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterPartialUIData
import com.mx.liftechnology.registroeducativo.main.ui.components.AlertDialogConfirm
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.RegisterPartialList
import com.mx.liftechnology.registroeducativo.main.ui.components.SpinnerOutlinedTextField
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun RegisterPartialScreen(
    navController: NavHostController,
    registerPartialViewModel: RegisterPartialViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel
) {

    val uiState by registerPartialViewModel.uiState.collectAsStateWithLifecycle()
    val uiData by registerPartialViewModel.uiData.collectAsStateWithLifecycle()

    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(uiState.uiState) {
        if (uiState.uiState == ModelStateUIEnum.SUCCESS)  navController.popBackStack()
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
        logs("Register partial")
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
                onPartialChanged = { registerPartialViewModel.onPartialChanged(it) }
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

@Composable
private fun HeaderRegisterPartial(
    navigate : () -> Unit
) {
    ComponentHeaderBack(
        title = stringResource(R.string.register_partial),
        body = stringResource(R.string.register_subject_name_description_2),
        onReturnClick = { navigate()}
    )
}

@Composable
private fun BodyRegisterPartial(
    uiData: ModelRegisterPartialUIData,
    onPartialChanged: (String) -> Unit,
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
            SpinnerOutlinedTextField(
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

@Composable
private fun ColumnRegisterPartial(
    uiData: ModelRegisterPartialUIData,
    onDateChange: (
        Pair<Pair<LocalDate?, LocalDate?>, Int>,
    ) -> Unit,
) {
    RegisterPartialList(
        items = uiData.listCalendar!!,
        onDateChange = { onDateChange(it) })
}

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