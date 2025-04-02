package com.mx.liftechnology.registroeducativo.main.ui.activityMain.partial

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
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterPartialUIState
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.LoadingAnimation
import com.mx.liftechnology.registroeducativo.main.ui.components.RegisterPartialList
import com.mx.liftechnology.registroeducativo.main.ui.components.SpinnerOutlinedTextField
import com.mx.liftechnology.registroeducativo.main.ui.components.TextBody
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun RegisterPartialScreen(
    navController: NavHostController,
    registerPartialViewModel: RegisterPartialViewModel = koinViewModel(),
) {

    val uiState by registerPartialViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.popBackStack()
        }
    }
    LaunchedEffect(Unit) {
        registerPartialViewModel.getListPartialCompose()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.margin_outer))
    ) {

        HeaderRegisterPartial(navController = navController)

        BodyRegisterPartial(
            uiState = uiState,
            onPartialChanged = { registerPartialViewModel.onPartialChanged(it) }
        )

        if (uiState.numberPartials.isNotEmpty() && uiState.numberPartials.toInt() > 0) {
            ColumnRegisterPartial(
                uiState = uiState,
                onDateChange = { registerPartialViewModel.onDateChange(it) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        ActionRegisterPartial { registerPartialViewModel.validateFieldsCompose() }
    }
    LoadingAnimation(uiState.isLoading)

}

@Composable
private fun HeaderRegisterPartial(navController: NavHostController) {
    ComponentHeaderBack(
        title = stringResource(R.string.register_partial),
        body = stringResource(R.string.register_subject_name_description_2),
    ) { navController.popBackStack() }
}

@Composable
private fun BodyRegisterPartial(
    uiState: ModelRegisterPartialUIState,
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
                options = uiState.listOptions,
                selectedOption = uiState.numberPartials,
                read = uiState.read,
                label = stringResource(id = R.string.register_partial_period),
                error = uiState.isErrorOption,
                onOptionSelected = { onPartialChanged(it) }
            )
        }
    }

    CustomSpace(dimensionResource(R.dimen.margin_between))
}

@Composable
private fun ColumnRegisterPartial(
    uiState: ModelRegisterPartialUIState,
    onDateChange: (
        Pair<Pair<LocalDate?, LocalDate?>, Int>,
    ) -> Unit,
) {
    RegisterPartialList(
        items = uiState.listCalendar!!,
        onDateChange = { onDateChange(it) })
}

@Composable
private fun ActionRegisterPartial(
    validateFieldsCompose: () -> Unit,
) {
    ButtonAction(
        containerColor = color_action,
        text = stringResource(R.string.add_button),
        onActionClick = { validateFieldsCompose() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}