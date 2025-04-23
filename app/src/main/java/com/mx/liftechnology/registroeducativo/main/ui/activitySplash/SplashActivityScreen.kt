package com.mx.liftechnology.registroeducativo.main.ui.activitySplash

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel


@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            splashViewModel.onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    // Pedimos permiso cuando entra
    LaunchedEffect(Unit) {
        splashViewModel.requestLocationPermission(permissionLauncher)
    }

    // Observa hacia dónde navegar
    val navigate by splashViewModel.navigate.collectAsState()

    LaunchedEffect(navigate) {
        when (navigate) {
            true -> onNavigateToMain()
            false -> onNavigateToLogin()
            null -> {onPermissionDenied()}
        }
    }

    // UI visible del splash
   /* Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.first_logo))
        val progress by animateLottieCompositionAsState(composition)

        Box(
            modifier = Modifier
                .fillMaxSize()
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
    }*/
}