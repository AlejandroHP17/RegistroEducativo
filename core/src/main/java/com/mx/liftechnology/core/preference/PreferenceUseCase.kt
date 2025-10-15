/**
 * @file Define el caso de uso para gestionar las preferencias de la aplicación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.preference

/**
 * Caso de uso para gestionar el acceso a SharedPreferences.
 * Esta clase abstrae la lógica para guardar y obtener diferentes tipos de datos de las preferencias.
 *
 * @property preference El repositorio para acceder a las preferencias.
 * @author Pelkidev
 * @version 1.0.0
 */
class PreferenceUseCase(private val preference: PreferenceRepository) {

    /**
     * Guarda un valor booleano en las preferencias.
     *
     * @param name El nombre de la preferencia a guardar.
     * @param value El valor a guardar.
     */
    fun savePreferenceBoolean(name: String, value: Boolean) {
        preference.savePreference(name, value)
    }

    /**
     * Obtiene un valor booleano de las preferencias.
     *
     * @param name El nombre de la preferencia a obtener.
     * @return El valor booleano guardado, o `false` si no se encuentra.
     */
    fun getPreferenceBoolean(name: String): Boolean {
        return preference.getPreference(name, false)
    }

    /**
     * Guarda un valor de tipo String en las preferencias.
     *
     * @param name El nombre de la preferencia a guardar.
     * @param value El valor a guardar.
     */
    fun savePreferenceString(name: String, value: String?) {
        preference.savePreference(name, value)
    }

    /**
     * Obtiene un valor de tipo String de las preferencias.
     *
     * @param name El nombre de la preferencia a obtener.
     * @return El valor de tipo String guardado, o `null` si no se encuentra.
     */
    fun getPreferenceString(name: String): String? {
        return preference.getPreference(name, null)
    }

    /**
     * Guarda un valor entero en las preferencias.
     *
     * @param name El nombre de la preferencia a guardar.
     * @param value El valor a guardar.
     */
    fun savePreferenceInt(name: String, value: Int?) {
        preference.savePreference(name, value)
    }

    /**
     * Obtiene un valor entero de las preferencias.
     *
     * @param name El nombre de la preferencia a obtener.
     * @return El valor entero guardado, o `-1` si no se encuentra.
     */
    fun getPreferenceInt(name: String): Int? {
        return preference.getPreference(name, -1)
    }

    /**
     * Limpia todas las preferencias guardadas.
     *
     * @return `true` si las preferencias se limpiaron correctamente, `false` en caso contrario.
     */
    fun cleanPreference(): Boolean {
        return preference.cleanPreference()
    }
}