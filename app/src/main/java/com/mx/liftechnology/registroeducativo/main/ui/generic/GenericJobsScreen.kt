package com.mx.liftechnology.registroeducativo.main.ui.generic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelWotyFofiDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelWotyFofiUiCallbacks
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelComplexCard
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
    dataState: ModelWotyFofiDataState,
    onReturnClick: () -> Unit,
    complexCallbacks: ModelWotyFofiUiCallbacks,
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
            complexCallbacks = ModelWotyFofiUiCallbacks(
                onExpandedTitle = { complexCallbacks.onExpandedTitle(it) },
                onExpandedSubTitle = { subItem, parentItem -> complexCallbacks.onExpandedSubTitle(subItem, parentItem) },
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
    dataState: ModelWotyFofiDataState,
    complexCallbacks: ModelWotyFofiUiCallbacks
){
    LazyColumn(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        itemsIndexed(
            items = dataState.dataCard ?: emptyList(),
            key = { _, item: ModelComplexCard -> item.idTitle!! }
        ) { _, item: ModelComplexCard ->
            ComplexCard(
                item = item,
                complexCallbacks = ModelWotyFofiUiCallbacks(
                    onExpandedTitle = { complexCallbacks.onExpandedTitle(it) },
                    onExpandedSubTitle = {subItem, parentItem ->   complexCallbacks.onExpandedSubTitle(subItem, parentItem) },
                )
            )
        }
    }
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

@Preview(showBackground = true)
@Composable
private fun GenericJobsScreenPreview(){
    GenericJobsScreen(
        title = "Alejandro",
        description = "Test de prueba",
        dataState = ModelWotyFofiDataState(),
        onReturnClick = {},
        complexCallbacks = ModelWotyFofiUiCallbacks(
            onExpandedTitle = {},
            onExpandedSubTitle = {subItem, parentItem ->  }
        ),
        onAction = {  }
    )
}
