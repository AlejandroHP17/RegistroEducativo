/**
 * @file Define la interfaz y la implementación del repositorio de preferencias, que gestiona el acceso a SharedPreferences de forma segura.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Interfaz para acceder y gestionar SharedPreferences.
 * Define los métodos para obtener, guardar y limpiar las preferencias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface PreferenceRepository {
    /**
     * Obtiene un valor de las preferencias.
     *
     * @param name El nombre de la preferencia a obtener.
     * @param default El valor por defecto a devolver si la preferencia no se encuentra.
     * @return El valor de la preferencia.
     */
    fun <T> getPreference(name: String, default: T): T

    /**
     * Guarda un valor en las preferencias.
     *
     * @param name El nombre de la preferencia a guardar.
     * @param value El valor a guardar.
     */
    fun <T> savePreference(name: String, value: T)

    /**
     * Limpia todas las preferencias.
     *
     * @return `true` si las preferencias se limpiaron correctamente, `false` en caso contrario.
     */
    fun cleanPreference() : Boolean
}

/**
 * Implementación de [PreferenceRepository] que utiliza [EncryptedSharedPreferences] para el almacenamiento seguro.
 * Se encarga de inicializar las preferencias encriptadas y de gestionar las operaciones de lectura y escritura.
 *
 * @property applicationContext El contexto de la aplicación.
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
            apply()
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