package com.mx.liftechnology.registroeducativo.main.ui.activitySplash

import android.location.Location
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.LocationHelper
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val preferenceUseCase: PreferenceUseCase,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _navigate = MutableStateFlow<Boolean?>(null)
    val navigate: StateFlow<Boolean?> = _navigate
    private var callback: LocationHelper.LocationCallback? = null

    fun getNavigation() {
        viewModelScope.launch(dispatcherProvider.io) {
            val isLoggedIn = preferenceUseCase.getPreferenceBoolean(ModelPreference.LOGIN)
            _navigate.value = isLoggedIn
        }
    }

    fun requestLocationPermission(launcher: ActivityResultLauncher<String>) {
        val cb = object : LocationHelper.LocationCallback {
            override fun onLocationResult(location: Location?) {
                getNavigation()
            }

            override fun onPermissionDenied() {
                _navigate.value = null
            }
        }

        callback = cb
        locationHelper.requestLocation(launcher, cb)
    }


    fun onPermissionGranted() {
        locationHelper.handlePermissionResult(true, callback!!)
    }
}