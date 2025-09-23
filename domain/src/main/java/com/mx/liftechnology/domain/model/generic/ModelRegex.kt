package com.mx.liftechnology.domain.model.generic

/** Model - Include all the elements to regex
 * @author pelkidev
 * @since 1.0.0
 */
object ModelRegex {
    val SIMPLE_TEXT = Regex("^[A-ZÁÉÍÓÚÑa-z0-9áéíóúñ ]+$")
    val COMPLEX_TEXT = Regex("^[A-ZÁÉÍÓÚÑa-z0-9áéíóúñ ,.\\-/]+$")
    val CURP = Regex("""^[A-Z]{4}\d{6}[HM][A-Z]{5}[A-Z\d]\d$""")
    val PHONE_NUMBER = Regex("""^\d{10}$""")
    val EMAIL = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    val PASS = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")
    val CCT = Regex("^[A-ZÑ0-9&]$")
    val SCORE = Regex("^(10([.,]0)?|10[.,]?|[0-9]([.,]\\d?)?)$")
}



