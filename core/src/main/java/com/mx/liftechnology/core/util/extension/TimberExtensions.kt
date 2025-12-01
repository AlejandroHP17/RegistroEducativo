/**
 * @file Proporciona funciones de extensión para logging utilizando Timber.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.util.extension

import timber.log.Timber

/**
 * Registra un mensaje de información en la consola utilizando Timber.
 * Esta función de extensión toma el nombre de la clase como tag para una fácil identificación.
 *
 * @param message El mensaje a registrar.
 * @param name Un prefijo opcional para el mensaje (por defecto: "Desarrollo: ").
 * @author Pelkidev
 * @version 1.0.0
 */
inline fun <reified T : Any> T.logInfo(message: String, name: String = "Desarrollo: ") {
        val tag = this::class.java.simpleName
        Timber.tag(tag).i("$name  $message")
}

/**
 * Registra un mensaje de depuración en la consola utilizando Timber.
 * Esta función de extensión toma el nombre de la clase como tag para una fácil identificación.
 *
 * @param message El mensaje a registrar.
 * @param name Un prefijo opcional para el mensaje (por defecto: "Desarrollo: ").
 * @author Pelkidev
 * @version 1.0.0
 */
inline fun <reified T : Any> T.logDebug(message: String, name: String = "Desarrollo: ") {
        val tag = this::class.java.simpleName
        Timber.tag(tag).d("$name  $message")
}

/**
 * @deprecated Usar logInfo() o logDebug() en su lugar. Esta función se mantiene por compatibilidad.
 */
@Deprecated("Usar logInfo() o logDebug() en su lugar", ReplaceWith("logInfo(message, name)"))
inline fun <reified T : Any> T.logs(message: String, name: String = "Desarrollo: ") {
        logInfo(message, name)
}

