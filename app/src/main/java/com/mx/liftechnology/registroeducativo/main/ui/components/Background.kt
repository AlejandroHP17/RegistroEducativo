package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_bg_first
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_bg_second
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_transparent

@Preview(showBackground = true)
@Composable
fun background(): Brush {
    return Brush.verticalGradient(
        colors = listOf(
            color_bg_first,
            color_bg_second
        )
    )
}

@Composable
fun LoadingAnimation(isVisible: Boolean) {
    if (isVisible) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.first_logo))
        val progress by animateLottieCompositionAsState(composition)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Fondo semi-transparente
                .clickable(enabled = false) {} // Evita toques mientras carga
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CustomSpace(dimen : Dp){
    Spacer(
        modifier = Modifier
            .height(dimen)
            .background(color_transparent)
    )
}