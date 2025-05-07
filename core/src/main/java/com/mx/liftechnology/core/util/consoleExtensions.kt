package com.mx.liftechnology.core.util

import timber.log.Timber

/** Print logs depending the flavor
 * @author pelkidev
 * @since 1.0.0
 */
// Función de extensión mejorada para registrar logs
inline fun <reified T : Any> T.log(message: String, name: String = "Desarrollo: ") {
        val tag = this::class.java.simpleName  // Obtiene el nombre real de la clase
        Timber.tag(tag).i("$name  $message")

}