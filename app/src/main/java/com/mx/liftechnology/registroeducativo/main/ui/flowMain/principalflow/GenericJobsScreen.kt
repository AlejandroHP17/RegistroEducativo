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

/**
 * A generic screen for displaying jobs.
 *
 * @param title The title of the screen.
 * @param description The description of the screen.
 * @param dataState The data state for the screen.
 * @param onReturnClick A lambda to be invoked when the back button is clicked.
 * @param complexCallbacks The callbacks for the complex card.
 * @param onAction A lambda to be invoked when the action button is clicked.
 */
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

/**
 * The header of the generic jobs screen.
 *
 * @param title The title of the header.
 * @param description The description of the header.
 * @param onReturnClick A lambda to be invoked when the back button is clicked.
 */
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

/**
 * The body of the generic jobs screen.
 *
 * @param dataState The data state for the screen.
 * @param complexCallbacks The callbacks for the complex card.
 */
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

/**
 * The action button of the generic jobs screen.
 *
 * @param onActionClick A lambda to be invoked when the action button is clicked.
 */
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