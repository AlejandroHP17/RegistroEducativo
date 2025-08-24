package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorApprove
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorDisable
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorDisabled
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorPrincipalText
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess


@Preview(showBackground = true)
@Composable
fun TestButton(){
    Column (
        modifier = Modifier.background(background())
    ){
        ButtonReturn({})
        ButtonAction(colorAction, "Pulsame",{}, true)
        ButtonActionShort(colorSuccess, "Pulsame"){}
        ButtonPair(colorAction, colorAction, "Pulsame", {}, {})
        ButtonsCalendar(colorDisabled, colorApprove, colorSuccess,true, {}, {})
    }
}

@Composable
fun ButtonsCalendar(
    colorStart: Color,
    colorEnd: Color,
    colorContinue : Color,
    disabledContinue: Boolean,
    onActionClick: (Int) -> Unit,
    onDismiss : () -> Unit
){
    Row (
        modifier = Modifier.fillMaxWidth().padding(horizontal = dimensionResource(id = R.dimen.margin_outer)),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        OutlinedButton(
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = colorStart,
                contentColor = colorPrincipalText
            ),
            onClick = { onActionClick(0) }
        )
        {Text(
            "Inicio")}

        OutlinedButton(
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = colorEnd,
                contentColor = colorPrincipalText
            ),
            onClick = { onActionClick(1) }
        )

        {Text("Fin")}

        OutlinedButton(
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = colorContinue,
                contentColor = colorPrincipalText,
                disabledContainerColor = colorDisabled,
                disabledContentColor = colorPrincipalText
            ),
            enabled = disabledContinue,
            onClick = {
                onActionClick(2)
                onDismiss()
            }
        )
        {Text("Confirmar")}
    }
}

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

@Composable
fun ButtonPair(
    actionColor:Color,
    recordColor:Color,
    text: String,
    onActionClick: () -> Unit,
    onRecordClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent))
    {
        val (action, spacer, record) = createRefs()

        // Botón de acción
        Button(
            onClick = onActionClick,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.touch_google))
                .constrainAs(action) {
                    start.linkTo(parent.start)
                    end.linkTo(spacer.start)
                    width = Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(20),
            border = BorderStroke(1.dp, colorPrincipalText),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorPrincipalText,
                containerColor = actionColor,
                disabledContentColor = colorPrincipalText,
                disabledContainerColor = colorDisable
            )
        ) {
            Text(text = text)
        }

        Spacer(modifier = Modifier
            .width(dimensionResource(id = R.dimen.margin_outer))
            .constrainAs(spacer){
                end.linkTo(record.start)
            })

        // Botón de grabación
        Button(
            onClick = onRecordClick,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.touch_google))
                .constrainAs(record) {
                    end.linkTo(parent.end)
                },
            enabled = true,
            shape = RoundedCornerShape(20),
            border = BorderStroke(1.dp, colorPrincipalText),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorPrincipalText,
                containerColor = recordColor,
                disabledContentColor = colorPrincipalText,
                disabledContainerColor = colorError
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_voice),
                contentDescription = "Record",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun CheckboxWithText(checked: Boolean, onCheckedChange: (Boolean) -> Unit, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(text = text)
    }
}
