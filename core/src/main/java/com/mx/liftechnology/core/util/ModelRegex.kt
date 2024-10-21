package com.mx.liftechnology.core.util

/** Model - Include all the elements to regex
 * @author pelkidev
 * @since 1.0.0
 */
object ModelRegex {
    val SIMPLE_TEXT = Regex("^[A-ZÁÉÍÓÚÑa-záéíóúñ ]$")
    val CURP = Regex("^[A-Z0-9]$")
    val PHONE_NUMBER = Regex("^[0-9]$")
    val EMAIL = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
}