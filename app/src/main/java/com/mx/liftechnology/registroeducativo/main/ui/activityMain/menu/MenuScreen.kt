package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.components.ComponentHeaderMenu
import com.mx.liftechnology.registroeducativo.main.ui.components.CustomSpace
import com.mx.liftechnology.registroeducativo.main.ui.components.TextHeader
import com.mx.liftechnology.registroeducativo.main.ui.components.background


@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    MenuScreen()
}

@Composable
fun MenuScreen(){
    Column(modifier = Modifier.fillMaxSize().background(background())) {
        ComponentHeaderMenu("hola", "mundo") { }

        CustomSpace(dimensionResource(id = R.dimen.margin_between))

        TextHeader("Area")

        CustomSpace(dimensionResource(id = R.dimen.margin_between))

        TextHeader("Area")
    }
}