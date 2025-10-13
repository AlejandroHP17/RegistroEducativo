package com.mx.liftechnology.core.util

import timber.log.Timber

/**
 * Logs a message using Timber, with the class name as the tag.
 *
 * @param message The message to log.
 * @param name A prefix for the message (defaults to "Desarrollo: ").
 *
 * @author Pelkidev
 * @version 1.0.0
 */
inline fun <reified T : Any> T.logs(message: String, name: String = "Desarrollo: ") {
        val tag = this::class.java.simpleName
        Timber.tag(tag).i("$name  $message")

}