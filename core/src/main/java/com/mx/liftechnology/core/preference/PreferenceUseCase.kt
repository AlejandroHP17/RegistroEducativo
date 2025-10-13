package com.mx.liftechnology.core.preference

/**
 * Use case for managing access to SharedPreferences.
 *
 * @property preference The repository for accessing preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class PreferenceUseCase(private val preference: PreferenceRepository) {

    /**
     * Saves a boolean value to preferences.
     *
     * @param name The name of the preference to save.
     * @param value The value to save.
     */
    fun savePreferenceBoolean(name: String, value: Boolean) {
        preference.savePreference(name, value)
    }

    /**
     * Gets a boolean value from preferences.
     *
     * @param name The name of the preference to get.
     * @return The saved boolean value, or false if not found.
     */
    fun getPreferenceBoolean(name: String): Boolean {
        return preference.getPreference(name, false)
    }

    /**
     * Saves a string value to preferences.
     *
     * @param name The name of the preference to save.
     * @param value The value to save.
     */
    fun savePreferenceString(name: String, value: String?) {
        preference.savePreference(name, value)
    }

    /**
     * Gets a string value from preferences.
     *
     * @param name The name of the preference to get.
     * @return The saved string value, or null if not found.
     */
    fun getPreferenceString(name: String): String? {
        return preference.getPreference(name, null)
    }

    /**
     * Saves an integer value to preferences.
     *
     * @param name The name of the preference to save.
     * @param value The value to save.
     */
    fun savePreferenceInt(name: String, value: Int?) {
        preference.savePreference(name, value)
    }

    /**
     * Gets an integer value from preferences.
     *
     * @param name The name of the preference to get.
     * @return The saved integer value, or -1 if not found.
     */
    fun getPreferenceInt(name: String): Int? {
        return preference.getPreference(name, -1)
    }

    /**
     * Clears all preferences.
     *
     * @return True if the preferences were cleared successfully, false otherwise.
     */
    fun cleanPreference(): Boolean {
        return preference.cleanPreference()
    }
}