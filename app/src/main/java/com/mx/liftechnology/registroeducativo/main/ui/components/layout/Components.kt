package com.mx.liftechnology.registroeducativo.main.ui.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorTransparent
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.TextHeader
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.TextDescription
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.TextLink
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonReturn
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.CheckboxWithText
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.background

/**
 * Función composable para previsualizar los componentes en este archivo.
 */
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

        ComponentHeaderMenu("Nos da gusto verte", "No tienes un ciclo activo", "parciales"){}
    }

}

/**
 * Componente de encabezado con botón de retroceso.
 *
 * @param title El título del encabezado.
 * @param body El texto del cuerpo del encabezado.
 * @param onReturnClick Lambda que se invoca cuando se hace clic en el botón de retroceso.
 */
@Composable
fun ComponentHeaderBack(title: String, body: String, onReturnClick: () -> Unit) {
    Column(
        modifier = Modifier.background(colorTransparent)
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

/**
 * Componente de encabezado con botón de retroceso pero sin texto de cuerpo.
 *
 * @param title El título del encabezado.
 * @param onReturnClick Lambda que se invoca cuando se hace clic en el botón de retroceso.
 */
@Composable
fun ComponentHeaderBackWithout(title: String, onReturnClick: () -> Unit) {
    Column(
        modifier = Modifier.background(colorTransparent)
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

/**
 * Componente de encabezado simple.
 *
 * @param title El título del encabezado.
 * @param body El texto del cuerpo del encabezado.
 */
@Composable
fun ComponentHeader(title: String, body: String) {
    Column(
        modifier = Modifier.background(colorTransparent)
    ) {

        CustomSpace(dimensionResource(id = R.dimen.margin_top_return))

        TextHeader(title)

        CustomSpace(dimensionResource(id = R.dimen.margin_between))

        TextDescription(body)

        CustomSpace(dimensionResource(id = R.dimen.margin_divided))
    }
}

/**
 * Componente con una mezcla de texto regular y texto clickeable.
 *
 * @param text El texto regular.
 * @param textClick El texto clickeable.
 * @param onTextClick Lambda que se invoca cuando se hace clic en el texto clickeable.
 */
@Composable
fun ComponentTextMix(text: String, textClick: String, onTextClick: () -> Unit) {
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

/**
 * Componente con un checkbox y texto clickeable.
 *
 * @param checkBox El estado marcado del checkbox.
 * @param checkBoxClick Lambda que se invoca cuando se hace clic en el checkbox.
 * @param textClick Lambda que se invoca cuando se hace clic en el texto clickeable.
 */
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

/**
 * Componente de encabezado para el ciclo escolar principal.
 *
 * @param title El título del encabezado.
 * @param body El texto del cuerpo del encabezado.
 * @param partial El texto para el parcial.
 * @param onClick Lambda que se invoca cuando se hace clic en el encabezado.
 */
@Composable
fun ComponentHeaderMenu(
    title: String,
    body: String,
    partial: String,
    onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorTransparent, // Color de fondo
            contentColor = colorTransparent
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.background(colorTransparent)
        ) {
            CustomSpace(dimensionResource(id = R.dimen.margin_between))

            TextDescription(title)

            CustomSpace(dimensionResource(id = R.dimen.margin_between))

            TextSubHeader(body)

            TextDescription(partial)

            CustomSpace(dimensionResource(id = R.dimen.margin_between))
        }
    }


}
