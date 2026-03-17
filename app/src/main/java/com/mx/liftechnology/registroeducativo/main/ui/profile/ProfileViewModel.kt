package com.mx.liftechnology.registroeducativo.main.ui.profile

import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.preference.PreferenceUseCase

/**
 * ViewModel para la pantalla de perfil de usuario.
 * 
 * Gestiona las operaciones relacionadas con el perfil del usuario, como el cierre de sesión.
 *
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ProfileViewModel(
    private val preference: PreferenceUseCase
) : ViewModel() {

    /**
     * Cierra la sesión del usuario limpiando todas las preferencias almacenadas.
     */
    fun closeSession() =
        preference.cleanPreference()

}
