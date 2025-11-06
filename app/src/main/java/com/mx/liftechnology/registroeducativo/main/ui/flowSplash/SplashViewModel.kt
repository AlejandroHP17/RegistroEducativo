package com.mx.liftechnology.registroeducativo.main.ui.flowSplash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Splash screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SplashViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val preferenceUseCase: PreferenceUseCase
) : ViewModel() {

    private val _navigate = MutableStateFlow<Boolean?>(null)
    /** Indicates whether navigation should occur and to where. */
    val navigate: StateFlow<Boolean?> = _navigate

    /**
     * Called when the required permissions have been granted.
     */
    fun onPermissionGranted() {
        getNavigation()
    }

    /**
     * Called when the required permissions have been denied.
     */
    fun onPermissionDenied() {
        _navigate.value = null
    }

    private fun getNavigation() {
        viewModelScope.launch(dispatcherProvider.io) {
            val isLoggedIn = preferenceUseCase.getPreferenceBoolean(ModelPreference.REMEMBER_LOGIN)
            _navigate.value = isLoggedIn
        }
    }

}