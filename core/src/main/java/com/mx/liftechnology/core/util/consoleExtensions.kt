/**
 * @file Proporciona funciones de extensión para logging, facilitando el registro de eventos en la consola.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.util

import timber.log.Timber

/**
 * Registra un mensaje en la consola utilizando Timber.
 * Esta función de extensión toma el nombre de la clase como tag para una fácil identificación.
 *
 * @param message El mensaje a registrar.
 * @param name Un prefijo opcional para el mensaje (por defecto: "Desarrollo: ").
 * @author Pelkidev
 * @version 1.0.0
 */
inline fun <reified T : Any> T.logs(message: String, name: String = "Desarrollo: ") {
        val tag = this::class.java.simpleName
        Timber.tag(tag).i("$name  $message")

}