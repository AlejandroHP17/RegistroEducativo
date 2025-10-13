package com.mx.liftechnology.core.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Interface for accessing and managing SharedPreferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface PreferenceRepository {
    /**
     * Gets a preference value.
     *
     * @param name The name of the preference to get.
     * @param default The default value to return if the preference is not found.
     * @return The preference value.
     */
    fun <T> getPreference(name: String, default: T): T

    /**
     * Saves a preference value.
     *
     * @param name The name of the preference to save.
     * @param value The value to save.
     */
    fun <T> savePreference(name: String, value: T)

    /**
     * Clears all preferences.
     *
     * @return True if the preferences were cleared successfully, false otherwise.
     */
    fun cleanPreference() : Boolean
}

/**
 * Implementation of [PreferenceRepository] that uses [EncryptedSharedPreferences] for secure storage.
 *
 * @property applicationContext The application context.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class PreferenceRepositoryImpl(
    private val applicationContext: Context,
) : PreferenceRepository {

    init {
        initPreferences()
    }

    private fun initPreferences() {
        try {
            val masterKey = MasterKey.Builder(applicationContext)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            securePrefs = EncryptedSharedPreferences.create(
                applicationContext,
                PREFS_FILENAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            applicationContext.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
                .edit {
                    clear()
                }

            val masterKey = MasterKey.Builder(applicationContext)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            securePrefs = EncryptedSharedPreferences.create(
                applicationContext,
                PREFS_FILENAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

    companion object {
        private const val PREFS_FILENAME = "secure_prefs"
        private lateinit var securePrefs: SharedPreferences
    }

    /**
     * {@inheritDoc}
     */
    override fun <T> getPreference(name: String, default: T): T {
        return with(securePrefs) {
            @Suppress("UNCHECKED_CAST")
            when (default) {
                is String? -> getString(name, default) as T
                is Int -> getInt(name, default) as T
                is Boolean -> getBoolean(name, default) as T
                is Float -> getFloat(name, default) as T
                is Long -> getLong(name, default) as T
                else -> throw IllegalArgumentException("Unsupported preference type")
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun <T> savePreference(name: String, value: T) {
        with(securePrefs.edit()) {
            when (value) {
                is String? -> putString(name, value)
                is Int -> putInt(name, value)
                is Boolean -> putBoolean(name, value)
                is Float -> putFloat(name, value)
                is Long -> putLong(name, value)
                else -> throw IllegalArgumentException("Unsupported preference type")
            }
            apply()  // Save the changes asynchronously
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun cleanPreference(): Boolean {
        securePrefs.edit { clear() }
        return true
    }
}