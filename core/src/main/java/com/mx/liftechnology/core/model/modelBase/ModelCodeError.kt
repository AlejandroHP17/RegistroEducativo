package com.mx.liftechnology.core.model.modelBase

/** Model - Include all the error element
 * @author pelkidev
 * @since 1.0.0
 */
object ModelCodeError {
    //  Valores de 100 - Servicio
    const val ERROR_FUNCTION: Int = 100
    const val ERROR_SERVICE: Int = 101


    //  Valores de 200 - Editext
    const val ET_EMPTY: Int = 200
    const val ET_FORMAT: Int = 201
    const val ET_DIFFERENT: Int = 202
    const val ET_NOT_FOUND: Int = 203
    const val ET_INCORRECT_DATA: Int = 203
    const val ET_INCORRECT_FORMAT: Int = 204

    const val ET_MISTAKE_EMAIL: Int = 210
    const val ET_MISTAKE_PASS: Int = 211
}
