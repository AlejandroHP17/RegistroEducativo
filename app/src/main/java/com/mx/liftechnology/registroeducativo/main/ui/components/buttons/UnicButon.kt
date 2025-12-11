package com.mx.liftechnology.registroeducativo.main.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.background
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorDisable
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorPrincipalText
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess

/**
 * A composable function for previewing the buttons in this file.
 */
@Preview(showBackground = true)
@Composable
private fun UnicButtonPreview(){
    Column (
        modifier = Modifier.background(background())
    ){
        ButtonReturn({})
        ButtonAction(colorAction, "Pulsame",{}, true)
        ButtonActionShort(colorSuccess, "Pulsame"){}
    }
}

/**
 * A back button component.
 *
 * @param onReturnClick A lambda to be invoked when the back button is clicked.
 */
@Composable
fun ButtonReturn(
    onReturnClick: () -> Unit,
){
    IconButton (
        onClick = onReturnClick,
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.touch_google))

    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_return),
            contentDescription = "Return",
            tint = colorPrincipalText,
            modifier = Modifier.size(32.dp),
        )
    }
}

/**
 * An action button component.
 *
 * @param containerColor The color of the button's container.
 * @param text The text to display on the button.
 * @param onActionClick A lambda to be invoked when the button is clicked.
 * @param isAvailable Whether the button is enabled.
 */
@Composable
fun ButtonAction(
    containerColor:Color,
    text: String,
    onActionClick: () -> Unit,
    isAvailable: Boolean = true,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent))
    {
        Button(
            onClick = { onActionClick() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20),
            border = BorderStroke(1.dp, colorPrincipalText),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorPrincipalText,
                containerColor = containerColor,
                disabledContentColor = colorPrincipalText,
                disabledContainerColor = colorDisable
            ),
            enabled = isAvailable
        ) {
            Text(text)
        }
    }
}

/**
 * A short action button component.
 *
 * @param containerColor The color of the button's container.
 * @param text The text to display on the button.
 * @param onActionClick A lambda to be invoked when the button is clicked.
 */
@Composable
fun ButtonActionShort(
    containerColor:Color,
    text: String,
    onActionClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent))
    {
        Button(
            onClick = { onActionClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            shape = RoundedCornerShape(20),
            border = BorderStroke(1.dp, colorPrincipalText),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorPrincipalText,
                containerColor = containerColor,
                disabledContentColor = colorPrincipalText,
                disabledContainerColor = colorError
            )
        ) {
            Text(text)
        }
    }
}