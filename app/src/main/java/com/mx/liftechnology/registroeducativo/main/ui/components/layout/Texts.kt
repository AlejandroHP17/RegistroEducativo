package com.mx.liftechnology.registroeducativo.main.ui.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorAction
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorPrincipalText
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorTransparent
import com.mx.liftechnology.registroeducativo.main.ui.components.layout.background

/**
 * Función composable para previsualizar los componentes de texto en este archivo.
 */
@Preview(showBackground = true)
@Composable
fun TestText() {
    Column(
        modifier = Modifier.background(background())
    ) {
        TextHeader(
            "Titulo"
        )

        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.margin_outer))
                .background(colorTransparent)
        )
        TextSubHeader(
            "Titulo"
        )

        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.margin_outer))
                .background(colorTransparent)
        )

        TextDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.")

        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.margin_outer))
                .background(colorTransparent)
        )

        TextLink("Lorem ipsum dolor sit amet") {}

        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.margin_outer))
                .background(colorTransparent)
        )

        TextBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
         Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.margin_outer))
                .background(colorTransparent)
        )

        TextTitleDialog("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
    }
}

/**
 * Composable que muestra un texto de encabezado.
 *
 * @param title El texto a mostrar.
 */
@Composable
fun TextHeader(
    title: String
) {
    Text(
        text = title,
        color = colorPrincipalText,
        fontSize = dimensionResource(id = R.dimen.text_size_title).value.sp,
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * Composable que muestra un texto de sub-encabezado.
 *
 * @param title El texto a mostrar.
 */
@Composable
fun TextSubHeader(
    title: String
) {
    Text(
        text = title,
        color = colorPrincipalText,
        fontSize = dimensionResource(id = R.dimen.text_size_subtitle).value.sp,
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * Composable que muestra un texto de descripción.
 *
 * @param text El texto a mostrar.
 * @param modifier El modificador a aplicar al componente.
 */
@Composable
fun TextDescription(
    text: String,
    modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = colorPrincipalText,
        fontSize = dimensionResource(id = R.dimen.text_size_body).value.sp,
        modifier = modifier
            .fillMaxWidth()
    )
}

/**
 * Composable que muestra un texto de enlace clickeable.
 *
 * @param text El texto a mostrar.
 * @param onTextClick Lambda que se invoca cuando se hace clic en el texto.
 */
@Composable
fun TextLink(text: String, onTextClick: () -> Unit) {
    Text(
        text = text,
        color = colorAction,
        modifier = Modifier
            .clickable { onTextClick() }
    )
}

/**
 * Composable que muestra un texto de cuerpo.
 *
 * @param text El texto a mostrar.
 */
@Composable
fun TextBody(text: String) {
    Text(
        text = text,
        color = colorPrincipalText,
        fontSize = dimensionResource(id = R.dimen.text_size_form).value.sp,
        modifier = Modifier
            .fillMaxWidth()
    )
}

/**
 * Composable que muestra un texto de título para un diálogo.
 *
 * @param text El texto a mostrar.
 */
@Composable
fun TextTitleDialog(text: String) {
    Text(
        text = text,
        color = colorPrincipalText,
        fontSize = dimensionResource(id = R.dimen.text_size_title_dialog).value.sp,
        modifier = Modifier
            .fillMaxWidth()
    )
}
