package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CustomToast(
    message: String,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    if (isVisible) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Black.copy(alpha = 0.8f), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = message,
                color = Color.White,
                //style = MaterialTheme.typography.body1,
                //textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ShowCustomToast() {
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showToast = true
        delay(2000) // Muestra el Toast por 2 segundos
        showToast = false
    }

    CustomToast(
        message = "¡Esto es un toast personalizado!",
        isVisible = showToast,
        //modifier = Modifier.align(Alignment.BottomCenter) // Puedes cambiar la posición
    )
}