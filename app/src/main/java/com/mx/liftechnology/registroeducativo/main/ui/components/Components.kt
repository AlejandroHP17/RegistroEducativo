package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_transparent

@Preview(showBackground = true)
@Composable
fun TestComponents() {
    Column (
        Modifier.background(background())
    ){
        ComponentHeader("hola", "mundo")

        ComponentHeaderBack("hola", "mundo") {}

        ComponentTextMix("hola", "mundo"){}

        ComponentCheckBoxAndText(false,{},{})

        ComponentHeaderMenu("Nos da gusto verte", "No tienes un ciclo activo"){}
    }

}

@Composable
fun ComponentHeaderBack(title: String, body: String, onReturnClick: () -> Unit) {
    Column(
        modifier = Modifier.background(color_transparent)
    ) {
        CustomSpace(dimensionResource(id = R.dimen.margin_top_return))

        ButtonReturn {
            onReturnClick()
        }

        CustomSpace(dimensionResource(id = R.dimen.margin_between))

        TextHeader(title)

        CustomSpace(dimensionResource(id = R.dimen.margin_between))

        TextDescription(body)

        CustomSpace(dimensionResource(id = R.dimen.margin_divided))
    }
}

@Composable
fun ComponentHeaderBackWithout(title: String, onReturnClick: () -> Unit) {
    Column(
        modifier = Modifier.background(color_transparent)
    ) {
        CustomSpace(dimensionResource(id = R.dimen.margin_top_return))

        ButtonReturn {
            onReturnClick()
        }

        CustomSpace(dimensionResource(id = R.dimen.margin_between))

        TextHeader(title)

        CustomSpace(dimensionResource(id = R.dimen.margin_divided))
    }
}

@Composable
fun ComponentHeader(title: String, body: String) {
    Column(
        modifier = Modifier.background(color_transparent)
    ) {

        CustomSpace(dimensionResource(id = R.dimen.margin_top_return))

        TextHeader(title)

        CustomSpace(dimensionResource(id = R.dimen.margin_between))

        TextDescription(body)

        CustomSpace(dimensionResource(id = R.dimen.margin_divided))
    }
}

@Composable
fun ComponentTextMix(text: String, textClick: String, onTextClick: () -> Unit) {
    /** Footer - Registrarse  */
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.margin_outer)),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.width(4.dp))
        TextLink(textClick) {
            onTextClick()
        }
    }
}

@Composable
fun ComponentCheckBoxAndText(
    checkBox: Boolean,
    checkBoxClick: (Boolean) -> Unit,
    textClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        val (boxCheck, boxText) = createRefs()

        Box(modifier = Modifier.constrainAs(boxCheck) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
        }) {
            CheckboxWithText(
                checked = checkBox,
                onCheckedChange = {
                    checkBoxClick(it)
                },
                text = stringResource(id = R.string.log_remember),
            )
        }
        Box(modifier = Modifier.constrainAs(boxText) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }) {
            TextLink(stringResource(id = R.string.log_forgot)) {
                textClick()
            }
        }
    }
}

@Composable
fun ComponentHeaderMenu(
    title: String,
    body: String,
    onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = color_transparent, // Color de fondo
            contentColor = color_transparent
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.background(color_transparent)
        ) {
            CustomSpace(dimensionResource(id = R.dimen.margin_between))

            TextDescription(title)

            CustomSpace(dimensionResource(id = R.dimen.margin_between))

            TextSubHeader(body)

            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.margin_between))
                    .background(color_transparent)
            )
        }
    }


}
