package com.mx.liftechnology.core.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

interface PreferenceRepository {
    fun <T> getPreference(name: String, default: T): T
    fun <T> savePreference(name: String, value: T)
    fun cleanPreference() : Boolean
}

class PreferenceRepositoryImpl(
    private val applicationContext: Context,
) : PreferenceRepository {

    init {
        initPreferences()
    }

    /** initPreferences - Start the preferences
     * @author pelkidev
     * @since 1.0.0
     * */
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
            // Si hay un error al desencriptar, eliminar las preferencias corruptas
            applicationContext.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
                .edit {
                    clear()
                }

            // Intentar de nuevo después de borrar las preferencias corruptas
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

    override fun cleanPreference(): Boolean {
        securePrefs.edit { clear() }
        return true
    }
}