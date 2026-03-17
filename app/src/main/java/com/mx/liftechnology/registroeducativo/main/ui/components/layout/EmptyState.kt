package com.mx.liftechnology.registroeducativo.main.ui.components.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonActionShort
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonReturn
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorTransparent

/**
 * A composable function for previewing the empty state view.
 */
@Preview(showBackground = true)
@Composable
private fun EmptyStateView() {
    EmptyState(
        painterResource(id = R.drawable.ic_empty_formative_field),
        stringResource(R.string.empty_formative_field_1),
        stringResource(R.string.empty_formative_field_1),
        stringResource(R.string.add_button),
        {}) {}
}

/**
 * A composable that shows an empty state view.
 *
 * @param image The image to display.
 * @param title The title of the empty state.
 * @param description The description of the empty state.
 * @param button The text for the action button.
 * @param onReturnClick A lambda to be invoked when the back button is clicked.
 * @param onActionClick A lambda to be invoked when the action button is clicked.
 */
@Composable
fun EmptyState(
    image: Painter,
    title: String,
    description: String,
    button: String,
    onReturnClick: () -> Unit,
    onActionClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorTransparent),
    ) {
        val (returnBox, bodyBox) = createRefs()

        Column(
            modifier = Modifier
                .constrainAs(returnBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }) {
            CustomSpace(dimensionResource(id = R.dimen.margin_top_return))
            ButtonReturn {onReturnClick()}
        }

        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.margin_outer))
                .constrainAs(bodyBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }) {

            Image(
                painter = image,
                contentDescription = stringResource(R.string.tools_image),
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )

            CustomSpace(dimensionResource(id = R.dimen.margin_extra_outer))

            TextSubHeader(title)

            CustomSpace(dimensionResource(id = R.dimen.margin_outer))

            TextDescription(description)

            CustomSpace(dimensionResource(id = R.dimen.margin_extra_outer))

            ButtonActionShort(colorAction, button) { onActionClick() }
        }
    }

}