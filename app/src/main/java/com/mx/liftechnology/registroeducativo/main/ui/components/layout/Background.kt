package com.mx.liftechnology.registroeducativo.main.ui.components.layout

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorBgFirst
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorBgSecond
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorTransparent

/**
 * A composable function that returns a vertical gradient brush for the background.
 */
@Preview(showBackground = true)
@Composable
fun background(): Brush {
    return Brush.verticalGradient(
        colors = listOf(
            colorBgFirst,
            colorBgSecond
        )
    )
}

/**
 * A composable that shows a loading animation.
 *
 * @param isVisible Whether the animation is visible.
 */
@Composable
fun LoadingAnimation(isVisible: Boolean) {
    if (isVisible) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.first_logo))
        val progress by animateLottieCompositionAsState(composition)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(enabled = false) {}
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

/**
 * A composable that creates a custom space.
 *
 * @param dimen The height of the space.
 */
@Composable
fun CustomSpace(dimen: Dp) {
    Spacer(
        modifier = Modifier
            .height(dimen)
            .background(colorTransparent)
    )
}

/**
 * A composable that returns a modifier that adapts to the screen orientation.
 */
@Composable
fun ModifierOrientation():Modifier{
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    return if (isPortrait)
        Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))
    else
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensionResource(id = R.dimen.margin_outer))

}

