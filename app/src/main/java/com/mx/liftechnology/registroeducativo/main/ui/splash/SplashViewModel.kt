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
 * ViewModel para la pantalla de inicio (splash).
 * 
 * Gestiona la lógica de navegación inicial de la aplicación basándose en si el usuario
 * tiene una sesión guardada o no.
 *
 * @property dispatcherProvider El proveedor de dispatchers para controlar los hilos de ejecución.
 * @property preferenceUseCase El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SplashViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val preferenceUseCase: PreferenceUseCase
) : ViewModel() {

    private val _navigate = MutableStateFlow<Boolean?>(null)
    /** Indica si debe ocurrir la navegación y hacia dónde. `true` para ir al menú principal, `false` para ir al login, `null` si no hay navegación. */
    val navigate: StateFlow<Boolean?> = _navigate

    /**
     * Se llama cuando se han concedido los permisos requeridos.
     */
    fun onPermissionGranted() {
        getNavigation()
    }

    /**
     * Se llama cuando se han denegado los permisos requeridos.
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