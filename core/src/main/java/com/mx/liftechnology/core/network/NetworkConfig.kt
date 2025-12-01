/**
 * @file Contiene las constantes de configuración para la red.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network

/**
 * Objeto que contiene las constantes de configuración para las operaciones de red,
 * como timeouts de conexión, lectura y escritura.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object NetworkConfig {
    /** Timeout para establecer la conexión, en segundos. */
    const val CONNECT_TIMEOUT_SECONDS = 20L

    /** Timeout para leer datos de la conexión, en segundos. */
    const val READ_TIMEOUT_SECONDS = 20L

    /** Timeout para escribir datos en la conexión, en segundos. */
    const val WRITE_TIMEOUT_SECONDS = 20L
}

