/**
 * @file Define un almacenamiento seguro de tokens usando AndroidKeystore.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Clase para almacenar tokens de forma segura usando AndroidKeystore.
 * 
 * **Seguridad:**
 * Utiliza AndroidKeystore para almacenar y encriptar tokens de forma segura.
 * Los tokens se encriptan usando AES/GCM/NoPadding antes de almacenarse en SharedPreferences.
 * La clave de encriptación se almacena de forma segura en el hardware del dispositivo (si está disponible).
 * 
 * **Ventajas sobre EncryptedSharedPreferences:**
 * - Mayor seguridad: La clave se almacena en el hardware seguro del dispositivo
 * - Mejor rendimiento en dispositivos con hardware de seguridad
 * - Control más granular sobre la encriptación
 * 
 * **Nota:** Esta implementación es opcional y puede usarse para tokens muy sensibles.
 * Para la mayoría de casos, EncryptedSharedPreferences es suficiente.
 *
 * @property context El contexto de la aplicación.
 * @author Pelkidev
 * @version 1.0.0
 */
class SecureTokenStorage(private val context: Context) {

    companion object {
        private const val KEYSTORE_ALIAS = "TokenEncryptionKey"
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val GCM_IV_LENGTH = 12
        private const val GCM_TAG_LENGTH = 128
        private const val PREFS_NAME = "secure_token_prefs"
        private const val KEY_ACCESS_TOKEN = "encrypted_access_token"
        private const val KEY_REFRESH_TOKEN = "encrypted_refresh_token"
    }

    private val keyStore: KeyStore = KeyStore.getInstance(KEYSTORE_PROVIDER).apply {
        load(null)
    }

    /**
     * Obtiene o genera la clave de encriptación desde AndroidKeystore.
     *
     * @return La clave secreta para encriptar/desencriptar tokens.
     */
    private fun getSecretKey(): SecretKey {
        val existingKey = keyStore.getEntry(KEYSTORE_ALIAS, null) as? KeyStore.SecretKeyEntry
        if (existingKey != null) {
            return existingKey.secretKey
        }

        // Generar nueva clave si no existe
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_PROVIDER)
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEYSTORE_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }

    /**
     * Encripta un token usando la clave almacenada en AndroidKeystore.
     *
     * @param token El token a encriptar.
     * @return El token encriptado en formato Base64, o `null` si hay un error.
     */
    private fun encryptToken(token: String): String? {
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
            val iv = cipher.iv
            val encrypted = cipher.doFinal(token.toByteArray(Charsets.UTF_8))
            
            // Combinar IV + datos encriptados para almacenamiento
            val combined = ByteArray(iv.size + encrypted.size)
            System.arraycopy(iv, 0, combined, 0, iv.size)
            System.arraycopy(encrypted, 0, combined, iv.size, encrypted.size)
            
            Base64.encodeToString(combined, Base64.DEFAULT)
        } catch (e: Exception) {
            android.util.Log.e("SecureTokenStorage", "Error al encriptar token: ${e.message}", e)
            null
        }
    }

    /**
     * Desencripta un token usando la clave almacenada en AndroidKeystore.
     *
     * @param encryptedToken El token encriptado en formato Base64.
     * @return El token desencriptado, o `null` si hay un error.
     */
    private fun decryptToken(encryptedToken: String): String? {
        return try {
            val combined = Base64.decode(encryptedToken, Base64.DEFAULT)
            val iv = ByteArray(GCM_IV_LENGTH)
            val encrypted = ByteArray(combined.size - GCM_IV_LENGTH)
            
            System.arraycopy(combined, 0, iv, 0, GCM_IV_LENGTH)
            System.arraycopy(combined, GCM_IV_LENGTH, encrypted, 0, encrypted.size)
            
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
            
            String(cipher.doFinal(encrypted), Charsets.UTF_8)
        } catch (e: Exception) {
            android.util.Log.e("SecureTokenStorage", "Error al desencriptar token: ${e.message}", e)
            null
        }
    }

    /**
     * Guarda el token de acceso de forma segura.
     *
     * @param token El token de acceso a guardar.
     * @return `true` si se guardó correctamente, `false` en caso contrario.
     */
    fun saveAccessToken(token: String): Boolean {
        val encrypted = encryptToken(token) ?: return false
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.edit().putString(KEY_ACCESS_TOKEN, encrypted).commit()
    }

    /**
     * Obtiene el token de acceso de forma segura.
     *
     * @return El token de acceso desencriptado, o `null` si no existe o hay un error.
     */
    fun getAccessToken(): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val encrypted = prefs.getString(KEY_ACCESS_TOKEN, null) ?: return null
        return decryptToken(encrypted)
    }

    /**
     * Guarda el token de refresco de forma segura.
     *
     * @param token El token de refresco a guardar.
     * @return `true` si se guardó correctamente, `false` en caso contrario.
     */
    fun saveRefreshToken(token: String): Boolean {
        val encrypted = encryptToken(token) ?: return false
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.edit().putString(KEY_REFRESH_TOKEN, encrypted).commit()
    }

    /**
     * Obtiene el token de refresco de forma segura.
     *
     * @return El token de refresco desencriptado, o `null` si no existe o hay un error.
     */
    fun getRefreshToken(): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val encrypted = prefs.getString(KEY_REFRESH_TOKEN, null) ?: return null
        return decryptToken(encrypted)
    }

    /**
     * Elimina todos los tokens almacenados.
     *
     * @return `true` si se eliminaron correctamente, `false` en caso contrario.
     */
    fun clearTokens(): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.edit().clear().commit()
    }
}


