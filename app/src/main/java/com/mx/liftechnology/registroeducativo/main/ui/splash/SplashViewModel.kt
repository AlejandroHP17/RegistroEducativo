package com.mx.liftechnology.registroeducativo.main.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        viewModelScope.launch {
            // Las operaciones de I/O (SharedPreferences) deben ejecutarse en el dispatcher de I/O
            val isLoggedIn = withContext(dispatcherProvider.io) {
                preferenceUseCase.getRememberLogin()
            }
            _navigate.value = isLoggedIn
        }
    }

}