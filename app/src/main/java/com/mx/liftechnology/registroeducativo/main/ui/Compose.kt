package com.mx.liftechnology.registroeducativo.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mx.liftechnology.registroeducativo.main.ui.components.background

class Compose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box( // Usa Box en lugar de Surface
                modifier = Modifier
                    .fillMaxSize()
                    .background(background()) // Aplica el fondo aquí
            ) {
                ///LoginScreen()
            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun ExerciseScreen() {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (yellowBox, magentaBox, greenBox, grayBox, blueBox, redBox, cyanBox, darkBox, blackBox) = createRefs()

        Box(modifier = Modifier
            .size(75.dp)
            .background(Color.Yellow)
            .constrainAs(yellowBox) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }) {}

        Box(modifier = Modifier
            .size(150.dp)
            .background(Color.Blue)
            .constrainAs(blueBox) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(yellowBox.bottom)
            }) {}

        Box(modifier = Modifier
            .size(75.dp)
            .background(Color.Magenta)
            .constrainAs(magentaBox) {
                end.linkTo(yellowBox.start)
                bottom.linkTo(yellowBox.top)
            }) {}

        Box(modifier = Modifier
            .size(75.dp)
            .background(Color.Green)
            .constrainAs(greenBox) {
                start.linkTo(yellowBox.end)
                bottom.linkTo(yellowBox.top)
            }) {}

        Box(modifier = Modifier
            .size(75.dp)
            .background(Color.Gray)
            .constrainAs(grayBox) {
                top.linkTo(yellowBox.bottom)
                end.linkTo(yellowBox.start)
            }) {}

        Box(modifier = Modifier
            .size(75.dp)
            .background(Color.Red)
            .constrainAs(redBox) {
                top.linkTo(yellowBox.bottom)
                start.linkTo(yellowBox.end)
            }) {}

        Box(modifier = Modifier
            .size(150.dp)
            .background(Color.Cyan)
            .constrainAs(cyanBox) {
                end.linkTo(yellowBox.start)
                bottom.linkTo(magentaBox.top)
            }) {}

        Box(modifier = Modifier
            .size(150.dp)
            .background(Color.DarkGray)
            .constrainAs(darkBox) {
                start.linkTo(yellowBox.end)
                bottom.linkTo(magentaBox.top)
            }) {}

        Box(modifier = Modifier
            .size(75.dp)
            .background(Color.Black)
            .constrainAs(blackBox) {
                start.linkTo(cyanBox.end)
                end.linkTo(darkBox.start)
                top.linkTo(cyanBox.top)
                bottom.linkTo(cyanBox.bottom)
            }) {}

    }

}
