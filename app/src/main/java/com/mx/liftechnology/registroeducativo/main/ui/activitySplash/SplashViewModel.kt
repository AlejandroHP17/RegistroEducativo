package com.mx.liftechnology.registroeducativo.main.ui.activitySplash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val preferenceUseCase: PreferenceUseCase
) : ViewModel() {

    private val _navigate = MutableStateFlow<Boolean?>(null)
    val navigate: StateFlow<Boolean?> = _navigate

    fun onPermissionGranted() {
        getNavigation()
    }

    fun onPermissionDenied() {
        _navigate.value = null
    }

    private fun getNavigation() {
        viewModelScope.launch(dispatcherProvider.io) {
            val isLoggedIn = preferenceUseCase.getPreferenceBoolean(ModelPreference.LOGIN)
            _navigate.value = isLoggedIn
        }
    }

}