package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelAssignmentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelAssignmentUiCallbacks
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComplexCard
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBack
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction

@Composable
fun GenericJobsScreen(
    title: String,
    description: String,
    dataState: ModelAssignmentDataState,
    onReturnClick: () -> Unit,
    complexCallbacks: ModelAssignmentUiCallbacks,
    onAction: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        GenericHeaderAssignment(
            title = title,
            description = description,
            onReturnClick = { onReturnClick() }
        )
        BodyAssignment(
            dataState =  dataState,
            complexCallbacks = ModelAssignmentUiCallbacks(
                onExpandedTitle = { complexCallbacks.onExpandedTitle(it) },
                onExpandedSubTitle = { complexCallbacks.onExpandedSubTitle(it) },
                onItemClick = { complexCallbacks.onItemClick (it)}
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        ActionAssignment { onAction() }
    }
}


@Composable
private fun GenericHeaderAssignment(
    title: String,
    description: String,
    onReturnClick: () -> Unit,
) {
    ComponentHeaderBack(
        title = title,
        body = description,
        onReturnClick = { onReturnClick() }
    )
}

@Composable
private fun BodyAssignment(
    dataState: ModelAssignmentDataState,
    complexCallbacks: ModelAssignmentUiCallbacks
){
    ComplexCard(
        item = dataState.dataCard,
        complexCallbacks = ModelAssignmentUiCallbacks(
            onExpandedTitle = { complexCallbacks.onExpandedTitle(it) },
            onExpandedSubTitle = { complexCallbacks.onExpandedSubTitle(it) },
            onItemClick = { complexCallbacks.onItemClick(it) }
        )
    )
}

@Composable
private fun ActionAssignment(
    onActionClick:() -> Unit
) {
    ButtonAction(
        containerColor = colorAction,
        text = stringResource(R.string.add_button),
        onActionClick = { onActionClick() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}