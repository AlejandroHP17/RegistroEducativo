package com.mx.liftechnology.registroeducativo.main.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_action
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
fun CustomSpace(dimen: Dp) {
    Spacer(
        modifier = Modifier
            .height(dimen)
            .background(color_transparent)
    )
}


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


@Preview(showBackground = true)
@Composable
private fun EmptyStateView() {
    EmptyState(
        painterResource(id = R.drawable.ic_empty_subject),
        stringResource(R.string.empty_subject_1),
        stringResource(R.string.empty_subject_1),
        stringResource(R.string.add_button),
        {}) {}
}

@Composable
fun EmptyState(
    image: Painter,
    title: String,
    description: String,
    button: String,
    onReturnClick: () -> Unit,
    onActionClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color_transparent),
    ) {
        val (returnBox, bodyBox) = createRefs()

        Column(
            modifier = Modifier
                .constrainAs(returnBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }) {
            CustomSpace(dimensionResource(id = R.dimen.margin_top_return))
            ButtonReturn {onReturnClick()}
        }

        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.margin_outer))
                .constrainAs(bodyBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }) {

            Image(
                painter = image,
                contentDescription = stringResource(R.string.tools_image),
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )

            CustomSpace(dimensionResource(id = R.dimen.margin_extra_outer))

            TextSubHeader(title)

            CustomSpace(dimensionResource(id = R.dimen.margin_outer))

            TextDescription(description)

            CustomSpace(dimensionResource(id = R.dimen.margin_extra_outer))

            ButtonActionShort(color_action, button) { onActionClick() }
        }
    }

}