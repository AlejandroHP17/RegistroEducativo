package com.mx.liftechnology.registroeducativo.main.ui.components

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
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_principal_text
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_transparent

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
                .background(color_transparent)
        )
        TextSubHeader(
            "Titulo"
        )

        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.margin_outer))
                .background(color_transparent)
        )

        TextDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.")

        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.margin_outer))
                .background(color_transparent)
        )

        TextLink("Lorem ipsum dolor sit amet") {}

        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.margin_outer))
                .background(color_transparent)
        )

        TextBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
    }
}

@Composable
fun TextHeader(
    title: String
) {
    Text(
        text = title,
        color = color_principal_text,
        fontSize = dimensionResource(id = R.dimen.text_size_title).value.sp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TextSubHeader(
    title: String
) {
    Text(
        text = title,
        color = color_principal_text,
        fontSize = dimensionResource(id = R.dimen.text_size_subtitle).value.sp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TextDescription(text: String) {
    Text(
        text = text,
        color = color_principal_text,
        fontSize = dimensionResource(id = R.dimen.text_size_body).value.sp,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun TextLink(text: String, onTextClick: () -> Unit) {
    Text(
        text = text,
        color = color_action,
        modifier = Modifier
            .clickable { onTextClick() }
    )
}

@Composable
fun TextBody(text: String) {
    Text(
        text = text,
        color = color_principal_text,
        fontSize = dimensionResource(id = R.dimen.text_size_form).value.sp,
        modifier = Modifier
            .fillMaxWidth()
    )
}
