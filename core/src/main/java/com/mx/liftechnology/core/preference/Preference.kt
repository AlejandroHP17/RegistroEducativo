/**
 * @file Define un sistema de tipos seguros para las preferencias.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.preference

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Clase sellada que representa una preferencia con tipo seguro.
 * Cada preferencia tiene su propia implementación que define cómo leer y escribir su valor.
 *
 * @param T El tipo de dato de la preferencia.
 * @author Pelkidev
 * @version 1.0.0
 */
sealed class Preference<T> {
    /** La clave de la preferencia en SharedPreferences. */
    abstract val key: String
    
    /**
     * Obtiene el valor de la preferencia desde SharedPreferences.
     *
     * @param prefs La instancia de SharedPreferences.
     * @return El valor de la preferencia, o `null` si no existe.
     */
    abstract fun get(prefs: SharedPreferences): T?
    
    /**
     * Guarda el valor de la preferencia en SharedPreferences.
     *
     * @param prefs La instancia de SharedPreferences.
     * @param value El valor a guardar.
     */
    abstract fun set(prefs: SharedPreferences, value: T)
    
    /**
     * Preferencia para el token de acceso.
     */
    object AccessToken : Preference<String>() {
        override val key = PreferenceKeys.ACCESS_TOKEN
        override fun get(prefs: SharedPreferences): String? = prefs.getString(key, null)
        override fun set(prefs: SharedPreferences, value: String) {
            prefs.edit { putString(key, value) }
        }
    }
    
    /**
     * Preferencia para el token de refresco.
     */
    object RefreshToken : Preference<String>() {
        override val key = PreferenceKeys.REFRESH_TOKEN
        override fun get(prefs: SharedPreferences): String? = prefs.getString(key, null)
        override fun set(prefs: SharedPreferences, value: String) {
            prefs.edit { putString(key, value) }
        }
    }
    
    /**
     * Preferencia para recordar el inicio de sesión.
     */
    object RememberLogin : Preference<Boolean>() {
        override val key = PreferenceKeys.REMEMBER_LOGIN
        override fun get(prefs: SharedPreferences): Boolean = prefs.getBoolean(key, false)
        override fun set(prefs: SharedPreferences, value: Boolean) {
            prefs.edit { putBoolean(key, value) }
        }
    }
    
    /**
     * Preferencia para el ID del usuario.
     */
    object IdUser : Preference<Int>() {
        override val key = PreferenceKeys.ID_USER
        override fun get(prefs: SharedPreferences): Int? = 
            if (prefs.contains(key)) prefs.getInt(key, -1) else null
        override fun set(prefs: SharedPreferences, value: Int) {
            prefs.edit { putInt(key, value) }
        }
    }
    
    /**
     * Preferencia para el ID del nivel de usuario.
     */
    object IdUserLevel : Preference<Int>() {
        override val key = PreferenceKeys.ID_USER_LEVEL
        override fun get(prefs: SharedPreferences): Int? = 
            if (prefs.contains(key)) prefs.getInt(key, -1) else null
        override fun set(prefs: SharedPreferences, value: Int) {
            prefs.edit { putInt(key, value) }
        }
    }
    
    /**
     * Preferencia para el ID del ciclo escolar.
     */
    object IdCycleSchool : Preference<Int>() {
        override val key = PreferenceKeys.ID_CYCLE_SCHOOL
        override fun get(prefs: SharedPreferences): Int? = 
            if (prefs.contains(key)) prefs.getInt(key, -1) else null
        override fun set(prefs: SharedPreferences, value: Int) {
            prefs.edit { putInt(key, value) }
        }
    }
    
    /**
     * Preferencia para el ID del campo formativo.
     */
    object IdFormativeField : Preference<Int>() {
        override val key = PreferenceKeys.ID_FORMATIVE_FIELD
        override fun get(prefs: SharedPreferences): Int? = 
            if (prefs.contains(key)) prefs.getInt(key, -1) else null
        override fun set(prefs: SharedPreferences, value: Int) {
            prefs.edit { putInt(key, value) }
        }
    }
    
    /**
     * Preferencia para el ID del parcial.
     */
    object IdPartial : Preference<Int>() {
        override val key = PreferenceKeys.ID_PARTIAL
        override fun get(prefs: SharedPreferences): Int? = 
            if (prefs.contains(key)) prefs.getInt(key, -1) else null
        override fun set(prefs: SharedPreferences, value: Int) {
            prefs.edit { putInt(key, value) }
        }
    }
    
    /**
     * Preferencia para el rango de fechas del parcial.
     */
    object RangeDatesPartial : Preference<String>() {
        override val key = PreferenceKeys.RANGE_DATES_PARTIAL
        override fun get(prefs: SharedPreferences): String? = prefs.getString(key, null)
        override fun set(prefs: SharedPreferences, value: String) {
            prefs.edit { putString(key, value) }
        }
    }
}

