/**
 * @file Define el proveedor del token de autenticación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.preference.PreferenceUseCase

/**
 * Clase que provee el token de autenticación desde las preferencias de usuario.
 * Encapsula la lógica para obtener el token, desacoplando su origen de los interceptores de red.
 *
 * @property preference El caso de uso para gestionar las preferencias de usuario.
 * @author Pelkidev
 * @version 1.0.0
 */
class TokenProvider(private val preference: PreferenceUseCase) {
    /**
     * Obtiene el token de autenticación guardado.
     *
     * @return El token, o `null` si no se encuentra ninguno.
     */
    fun getToken(): String? {
        return preference.getAccessToken()
    }

    fun getRefreshToken(): String?{
        return preference.getRefreshToken()
    }

    fun saveNewToken(token: String?){
        if (token != null) {
            preference.setAccessToken(token)
        }
    }

    fun closeSession() =
        preference.cleanPreference()


}