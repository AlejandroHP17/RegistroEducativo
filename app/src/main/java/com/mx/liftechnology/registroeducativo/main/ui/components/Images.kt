package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mx.liftechnology.registroeducativo.R

@Preview(showBackground = true)
@Composable
fun TestImages(){
    Column (
        modifier = Modifier.background(background())
    ){
        ImageLogo()
    }
}


@Composable
fun ImageLogo(){
    Image(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = "Logo",
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.margin_outer))
            .fillMaxWidth()
    )
}