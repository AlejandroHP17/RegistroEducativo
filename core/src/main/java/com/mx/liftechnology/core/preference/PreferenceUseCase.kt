/**
 * @file Define el caso de uso para gestionar las preferencias de la aplicación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.preference

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Caso de uso para gestionar el acceso a SharedPreferences.
 * 
 * **Responsabilidades:**
 * - Proporciona métodos de conveniencia para acceder a preferencias comunes
 * - Ofrece acceso con tipos seguros usando el sistema [Preference]
 * - Encapsula la lógica de negocio relacionada con preferencias
 * 
 * **Diferencia con [PreferenceRepository]:**
 * - [PreferenceRepository]: Acceso directo y genérico a SharedPreferences
 * - [PreferenceUseCase]: Métodos de conveniencia y tipos seguros para preferencias específicas
 *
 * @property preference El repositorio para acceder a las preferencias.
 * @author Pelkidev
 * @version 1.0.0
 */
class PreferenceUseCase(private val preference: PreferenceRepository) {
    
    private val prefs: SharedPreferences
        get() = preference.getSharedPreferences()

    /**
     * Limpia todas las preferencias guardadas.
     *
     * @return `true` si las preferencias se limpiaron correctamente, `false` en caso contrario.
     */
    fun cleanPreference(): Boolean {
        return preference.cleanPreference()
    }
    
    /**
     * Obtiene el valor de una preferencia usando el sistema de tipos seguros.
     *
     * @param preference La preferencia tipada a obtener.
     * @return El valor de la preferencia, o `null` si no existe.
     */
    fun <T> get(preference: Preference<T>): T? {
        return preference.get(prefs)
    }
    
    /**
     * Guarda el valor de una preferencia usando el sistema de tipos seguros.
     *
     * @param preference La preferencia tipada a guardar.
     * @param value El valor a guardar.
     */
    fun <T> set(preference: Preference<T>, value: T) {
        preference.set(prefs, value)
    }
    
    // ========== Métodos de conveniencia para preferencias comunes ==========
    
    /**
     * Obtiene el token de acceso usando tipos seguros.
     *
     * @return El token de acceso, o `null` si no existe.
     */
    fun getAccessToken(): String? = get(Preference.AccessToken)
    
    /**
     * Guarda el token de acceso usando tipos seguros.
     *
     * @param token El token de acceso a guardar.
     */
    fun setAccessToken(token: String) = set(Preference.AccessToken, token)
    
    /**
     * Obtiene el token de refresco usando tipos seguros.
     *
     * @return El token de refresco, o `null` si no existe.
     */
    fun getRefreshToken(): String? = get(Preference.RefreshToken)
    
    /**
     * Guarda el token de refresco usando tipos seguros.
     *
     * @param token El token de refresco a guardar.
     */
    fun setRefreshToken(token: String) = set(Preference.RefreshToken, token)
    
    /**
     * Obtiene el ID del usuario usando tipos seguros.
     *
     * @return El ID del usuario, o `null` si no existe.
     */
    fun getIdUser(): Int? = get(Preference.IdUser)
    
    /**
     * Guarda el ID del usuario usando tipos seguros.
     *
     * @param id El ID del usuario a guardar.
     */
    fun setIdUser(id: Int) = set(Preference.IdUser, id)
    
    /**
     * Obtiene el ID del ciclo escolar usando tipos seguros.
     *
     * @return El ID del ciclo escolar, o `null` si no existe.
     */
    fun getIdCycleSchool(): Int? = get(Preference.IdCycleSchool)
    
    /**
     * Guarda el ID del ciclo escolar usando tipos seguros.
     *
     * @param id El ID del ciclo escolar a guardar.
     */
    fun setIdCycleSchool(id: Int) = set(Preference.IdCycleSchool, id)
    
    /**
     * Obtiene el ID del campo formativo usando tipos seguros.
     *
     * @return El ID del campo formativo, o `null` si no existe.
     */
    fun getIdFormativeField(): Int? = get(Preference.IdFormativeField)
    
    /**
     * Guarda el ID del campo formativo usando tipos seguros.
     *
     * @param id El ID del campo formativo a guardar.
     */
    fun setIdFormativeField(id: Int) = set(Preference.IdFormativeField, id)
    
    /**
     * Obtiene el ID del parcial usando tipos seguros.
     *
     * @return El ID del parcial, o `null` si no existe.
     */
    fun getIdPartial(): Int? = get(Preference.IdPartial)
    
    /**
     * Guarda el ID del parcial usando tipos seguros.
     *
     * @param id El ID del parcial a guardar.
     */
    fun setIdPartial(id: Int) = set(Preference.IdPartial, id)
    
    /**
     * Obtiene el ID del nivel de usuario usando tipos seguros.
     *
     * @return El ID del nivel de usuario, o `null` si no existe.
     */
    fun getIdUserLevel(): Int? = get(Preference.IdUserLevel)
    
    /**
     * Guarda el ID del nivel de usuario usando tipos seguros.
     *
     * @param id El ID del nivel de usuario a guardar.
     */
    fun setIdUserLevel(id: Int) = set(Preference.IdUserLevel, id)
    
    /**
     * Obtiene el rango de fechas del parcial usando tipos seguros.
     *
     * @return El rango de fechas del parcial, o `null` si no existe.
     */
    fun getRangeDatesPartial(): String? = get(Preference.RangeDatesPartial)
    
    /**
     * Guarda el rango de fechas del parcial usando tipos seguros.
     *
     * @param range El rango de fechas del parcial a guardar.
     */
    fun setRangeDatesPartial(range: String?) {
        if (range != null) {
            set(Preference.RangeDatesPartial, range)
        } else {
            // Para eliminar, usamos el método directo
            prefs.edit { remove(Preference.RangeDatesPartial.key) }
        }
    }
    
    /**
     * Obtiene el estado de recordar inicio de sesión usando tipos seguros.
     *
     * @return `true` si se debe recordar el inicio de sesión, `false` en caso contrario.
     */
    fun getRememberLogin(): Boolean = get(Preference.RememberLogin) ?: false
    
    /**
     * Guarda el estado de recordar inicio de sesión usando tipos seguros.
     *
     * @param remember `true` para recordar el inicio de sesión, `false` en caso contrario.
     */
    fun setRememberLogin(remember: Boolean) = set(Preference.RememberLogin, remember)
}