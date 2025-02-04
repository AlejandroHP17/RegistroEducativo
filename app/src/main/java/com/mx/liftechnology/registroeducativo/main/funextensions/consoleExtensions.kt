package com.mx.liftechnology.registroeducativo.main.funextensions

import com.mx.liftechnology.registroeducativo.BuildConfig
import timber.log.Timber

/** Print logs depending the flavor
 * @author pelkidev
 * @since 1.0.0
 */
// Función de extensión mejorada para registrar logs
inline fun <reified T : Any> T.log(message: String, name: String = "Desarrollo: ") {
    if (BuildConfig.LOG_TAG) {
        val tag = this::class.java.simpleName  // Obtiene el nombre real de la clase
        Timber.tag(tag).i(name + message)
    }
}