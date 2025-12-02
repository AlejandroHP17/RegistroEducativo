/**
 * @file Proporciona utilidades para obtener identificadores únicos del dispositivo de forma segura.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.util.device

import android.content.Context
import android.provider.Settings

/**
 * Clase de ayuda para obtener identificadores únicos del dispositivo de forma segura.
 * 
 * **Seguridad:**
 * Utiliza `ANDROID_ID` que es un identificador único para cada combinación de aplicación y usuario
 * en el dispositivo. Es más confiable que usar `Build.FINGERPRINT + Build.ID` porque:
 * - Es único por aplicación y usuario
 * - No cambia a menos que se haga un factory reset
 * - No requiere permisos especiales
 * - Es más privado que el IMEI
 * 
 * **Nota:** En emuladores, `ANDROID_ID` puede ser el mismo para todos los emuladores,
 * pero en dispositivos reales es único.
 *
 * @property context El contexto de la aplicación.
 * @author Pelkidev
 * @version 1.0.0
 */
class DeviceIdHelper(private val context: Context) {

    /**
     * Obtiene el identificador único del dispositivo usando ANDROID_ID.
     * 
     * **Comportamiento:**
     * - En dispositivos reales: Retorna un ID único de 64 bits en formato hexadecimal
     * - En emuladores: Puede retornar el mismo ID para todos los emuladores
     * - Si no está disponible: Retorna un fallback basado en el package name
     * 
     * @return El identificador único del dispositivo, o un fallback si no está disponible.
     */
    fun getDeviceId(): String {
        return try {
            val androidId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            
            // ANDROID_ID puede ser null o "9774d56d682e549c" (valor conocido en algunos emuladores)
            // En ese caso, usamos un fallback
            if (androidId.isNullOrBlank() || androidId == "9774d56d682e549c") {
                getFallbackDeviceId()
            } else {
                androidId
            }
        } catch (e: Exception) {
            getFallbackDeviceId()
        }
    }

    /**
     * Genera un identificador de fallback cuando ANDROID_ID no está disponible.
     * 
     * @return Un identificador basado en el package name y propiedades del dispositivo.
     */
    private fun getFallbackDeviceId(): String {
        // Fallback: usar package name + timestamp de instalación si está disponible
        val packageName = context.packageName
        val installTime = try {
            val packageInfo = context.packageManager.getPackageInfo(packageName, 0)
            packageInfo.firstInstallTime.toString()
        } catch (e: Exception) {
            "unknown"
        }
        return "${packageName}_${installTime}".hashCode().toString(16)
    }
}

