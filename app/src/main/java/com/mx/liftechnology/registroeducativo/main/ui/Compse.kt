package com.mx.liftechnology.registroeducativo.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mx.liftechnology.registroeducativo.R

class Compse :ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Tu contenido de la pantalla de login, reemplazando todos los Views
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.ic_logo), contentDescription = null)

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Email") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {}) {
                Text("Login")
            }
        }

        if (true) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(16.dp))
                    .align(Alignment.Center)
            ) {
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExerciseScreen() {

    ConstraintLayout (modifier = Modifier.fillMaxSize()) {

        val (yellowBox,  magentaBox, greenBox, grayBox, blueBox, redBox, cyanBox,  darkBox, blackBox) = createRefs()

        Box(modifier = Modifier.size(75.dp).background(Color.Yellow).constrainAs(yellowBox){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }){}

        Box(modifier = Modifier.size(150.dp).background(Color.Blue).constrainAs(blueBox){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(yellowBox.bottom)
        }){}

        Box(modifier = Modifier.size(75.dp).background(Color.Magenta).constrainAs(magentaBox){
            end.linkTo(yellowBox.start)
            bottom.linkTo(yellowBox.top)
        }){}

        Box(modifier = Modifier.size(75.dp).background(Color.Green).constrainAs(greenBox){
            start.linkTo(yellowBox.end)
            bottom.linkTo(yellowBox.top)
        }){}

        Box(modifier = Modifier.size(75.dp).background(Color.Gray).constrainAs(grayBox){
            top.linkTo(yellowBox.bottom)
            end.linkTo(yellowBox.start)
        }){}

        Box(modifier = Modifier.size(75.dp).background(Color.Red).constrainAs(redBox){
            top.linkTo(yellowBox.bottom)
            start.linkTo(yellowBox.end)
        }){}

        Box(modifier = Modifier.size(150.dp).background(Color.Cyan).constrainAs(cyanBox){
            end.linkTo(yellowBox.start)
            bottom.linkTo(magentaBox.top)
        }){}

        Box(modifier = Modifier.size(150.dp).background(Color.DarkGray).constrainAs(darkBox){
            start.linkTo(yellowBox.end)
            bottom.linkTo(magentaBox.top)
        }){}

        Box(modifier = Modifier.size(75.dp).background(Color.Black).constrainAs(blackBox){
            start.linkTo(cyanBox.end)
            end.linkTo(darkBox.start)
            top.linkTo(cyanBox.top)
            bottom.linkTo(cyanBox.bottom)
        }){}

    }

}
