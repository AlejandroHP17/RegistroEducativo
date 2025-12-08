/**
 * @file Proporciona funciones de extensión para logging utilizando Timber.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.util.extension

import timber.log.Timber

const val log = "Desarrollo"

/**
 * Registra un mensaje de información en la consola utilizando Timber.
 * Esta función de extensión toma el nombre de la clase como tag para una fácil identificación.
 *
 * @param message El mensaje a registrar.
 * @param name Un prefijo opcional para el mensaje (por defecto: "Desarrollo: ").
 * @author Pelkidev
 * @version 1.0.0
 */
inline fun <reified T : Any> T.logInfo(message: String, name: String = "$log: ") {
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
inline fun <reified T : Any> T.logDebug(message: String, name: String = "$log: ") {
        val tag = this::class.java.simpleName
        Timber.tag(tag).d("$name  $message")
}