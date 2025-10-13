package com.mx.liftechnology.registroeducativo.main.ui.flowSplash

import android.app.Activity
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mx.liftechnology.core.util.LocationHelper
import org.koin.androidx.compose.koinViewModel

/**
 * The splash screen of the application.
 *
 * @param splashViewModel The ViewModel for this screen.
 * @param onNavigateToLogin A lambda to be invoked to navigate to the login screen.
 * @param onNavigateToMain A lambda to be invoked to navigate to the main screen.
 * @param onPermissionDenied A lambda to be invoked when the location permission is denied.
 */
@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val locationHelper = remember(activity) { activity?.let { LocationHelper(it) } }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            splashViewModel.onPermissionGranted()
        } else {
            splashViewModel.onPermissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        locationHelper?.requestLocation(permissionLauncher, object : LocationHelper.LocationCallback {
            override fun onLocationResult(location: Location?) {
                splashViewModel.onPermissionGranted()
            }

            override fun onPermissionDenied() {
                splashViewModel.onPermissionDenied()
            }
        })
    }

    val navigate by splashViewModel.navigate.collectAsStateWithLifecycle()
    LaunchedEffect(navigate) {
        when (navigate) {
            true -> onNavigateToMain()
            false -> onNavigateToLogin()
            null -> onPermissionDenied()
        }
    }

}