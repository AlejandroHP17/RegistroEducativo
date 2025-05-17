package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_error_toast
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_informative_toast
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_success_toast
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_warning_toast
import kotlinx.coroutines.delay

@Composable
fun ShowCustomAnimated(
    message: String,
    isVisible: Boolean,
    durationMillis: Long = 3000,
    typeToast: ModelStateTypeToastUI = ModelStateTypeToastUI.SUCCESS,
    onDismiss: () -> Unit,
) {
    // Manejo automático del tiempo de visibilidad
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(durationMillis)
            onDismiss()
        }
    }

    Box() {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentHeight()
                .zIndex(1f) // Asegura que quede arriba
                .statusBarsPadding()
                .align(Alignment.TopCenter)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        when (typeToast) {
                            ModelStateTypeToastUI.SUCCESS -> {
                                color_success_toast
                            }

                            ModelStateTypeToastUI.ERROR -> {
                                color_error_toast
                            }

                            ModelStateTypeToastUI.WARNING -> {
                                color_warning_toast
                            }

                            ModelStateTypeToastUI.INFORMATIVE -> {
                                color_informative_toast
                            }
                        }, RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(message, color = Color.White)
            }
        }
    }

}
