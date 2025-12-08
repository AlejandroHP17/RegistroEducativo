/**
 * @file Define las constantes para las claves utilizadas en el procesamiento de voz.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.generic

/**
 * Objeto que contiene las constantes para las claves de los datos extraídos del reconocimiento de voz.
 * Esto asegura que las claves sean consistentes al acceder a los datos en el mapa resultante.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ModelVoiceConstants {
    const val NAME = "nombre"
    const val LAST_NAME = "apellido paterno"
    const val SECOND_LAST_NAME = "apellido materno"
    const val CURP = "curp"
    const val BIRTHDAY = "fecha de nacimiento"
    const val PHONE_NUMBER = "número de contacto"
}
