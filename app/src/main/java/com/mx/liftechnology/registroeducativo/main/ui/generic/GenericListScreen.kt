package com.mx.liftechnology.registroeducativo.main.ui.generic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateSpinnerUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.ui.components.ButtonAction
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderBackWithout
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomCard
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.EmptyState
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction

/**
 * A generic screen for displaying a list of items.
 *
 * @param title The title of the screen.
 * @param textButton The text for the action button.
 * @param items The list of items to display.
 * @param onReturnClick A lambda to be invoked when the back button is clicked.
 * @param callbacks The callbacks for the list items.
 * @param onAction A lambda to be invoked when the action button is clicked.
 */
@Composable
fun GenericListScreen(
    title: String,
    textButton: String,
    items: List<ModelCustomCard>,
    onReturnClick: () -> Unit,
    callbacks: ModelStateSpinnerUI,
    onAction: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        val (header, column, action) = createRefs()

        Box(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            GenericHeaderList(
                title = title,
                onReturnClick = { onReturnClick() })
        }

        Box(
            modifier = Modifier.constrainAs(column) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(action.top)
                height = Dimension.fillToConstraints
            }) {
            BodyListGeneric(
                items = items,
                callbacks = callbacks
            )
        }

        Column(
            modifier = Modifier.constrainAs(action) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            ActionListGeneric(
                textButton = textButton,
                onActionClick = { onAction() }
            )
        }
    }


}

/**
 * A generic screen for displaying an empty state.
 *
 * @param image The image to display.
 * @param title The title of the empty state.
 * @param description The description of the empty state.
 * @param button The text for the action button.
 * @param onReturnClick A lambda to be invoked when the back button is clicked.
 * @param onActionClick A lambda to be invoked when the action button is clicked.
 */
@Composable
fun GenericEmptyScreen(
    image: Painter,
    title: String,
    description: String,
    button: String,
    onReturnClick: () -> Unit,
    onActionClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    ) {
        EmptyState(
            image = image,
            title = title,
            description = description,
            button = button,
            onReturnClick = { onReturnClick() },
            onActionClick = { onActionClick() }
        )
    }


}

/**
 * The header of the generic list screen.
 *
 * @param title The title of the header.
 * @param onReturnClick A lambda to be invoked when the back button is clicked.
 */
@Composable
private fun GenericHeaderList(
    title: String,
    onReturnClick: () -> Unit,
) {
    ComponentHeaderBackWithout(
        title = title,
        onReturnClick = { onReturnClick() }
    )
}

/**
 * The body of the generic list screen.
 *
 * @param items The list of items to display.
 * @param callbacks The callbacks for the list items.
 */
@Composable
fun BodyListGeneric(
    items: List<ModelCustomCard>,
    callbacks: ModelStateSpinnerUI,
) {
    LazyColumn(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_divided))
    ) {
        itemsIndexed(items, key = { _, item -> item.id }) { _, item ->
            CustomCard(
                item = item,
                callbacks = ModelStateSpinnerUI(
                    onItemClick = { callbacks.onItemClick(item) },
                    onEdit = { callbacks.onEdit(item) },
                    onDelete = { callbacks.onDelete(item) }
                )
            )
        }
    }
}

/**
 * The action button of the generic list screen.
 *
 * @param textButton The text for the action button.
 * @param onActionClick A lambda to be invoked when the action button is clicked.
 */
@Composable
private fun ActionListGeneric(
    textButton: String,
    onActionClick: () -> Unit,
) {
    CustomSpace(dimensionResource(R.dimen.margin_divided))
    ButtonAction(
        containerColor = colorAction,
        text = textButton,
        onActionClick = { onActionClick() }
    )
    CustomSpace(dimensionResource(R.dimen.margin_divided))
}