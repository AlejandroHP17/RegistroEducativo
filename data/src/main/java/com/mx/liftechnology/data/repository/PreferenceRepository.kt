package com.mx.liftechnology.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mx.liftechnology.core.model.ModelApi.User


interface PreferenceRepository {
    suspend fun <T> getPreference(name: String, default: T): T
    suspend fun <T> savePreference(name: String, value: T)
}

class PreferenceRepositoryImpl(
    private val applicationContext: Context
) : PreferenceRepository {

    init {
        initPreferences()
    }

    /** initPreferences - Start the preferences
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initPreferences() {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        securePrefs = EncryptedSharedPreferences.create(
            PREFS_FILENAME,
            masterKeyAlias,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        private const val PREFS_FILENAME = "secure_prefs"
        private lateinit var securePrefs: SharedPreferences
    }

    override suspend fun <T> getPreference(name: String, default: T): T {
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

    override suspend fun <T> savePreference(name: String, value: T) {
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
}