package com.mx.liftechnology.core.model.modelBase

/** Model - Include all the error element
 * @author pelkidev
 * @since 1.0.0
 */
object ModelCodeError {
    // Servicio
    const val ERROR_CATCH: String = "Error que entra al catch"
    const val ERROR_INCOMPLETE_DATA: String = "Datos incompletos"
    const val ERROR_TIMEOUT: String = "TiemOut"
    const val ERROR_UNAUTHORIZED: String = "Token expirado"
    const val ERROR_DATA: String = "Error CRUD"
    const val ERROR_UNKNOWN: String = "Error inesperado"
    const val ERROR_EMPTY: String = "Lista vacia"

    // Usuario
    const val ERROR_CRITICAL: String = "Ha ocurrido un error, intente más tarde"
    const val ERROR_VALIDATION_LOGIN: String = "Usuario o contraseña incorrectos, por favor valide nuevamente su información"
    const val ERROR_VALIDATION_REGISTER_USER: String = "No se ha podido registrar el correo. Verifique que los datos ingresados sean correctos"
    const val ERROR_VALIDATION_REGISTER_INFO: String = "Correo o código incorrectos, verifique su información"
    const val ERROR_VALIDATION: String = "No se logro actualizar la información, intente más tarde"


    //  Valores de 200 - Editext
    const val ET_EMPTY: Int = 200
    const val ET_FORMAT: Int = 201
    const val ET_DIFFERENT: Int = 202
    const val ET_NOT_FOUND: Int = 203
    const val ET_INCORRECT_FORMAT: Int = 204

    const val ET_MISTAKE_EMAIL: Int = 210
    const val ET_MISTAKE_PASS: Int = 211
    const val ET_MISTAKE_PASS_RULES: Int = 212
    const val ET_MISTAKE_CURP: Int = 213
    const val ET_MISTAKE_PHONE_NUMBER: Int = 214

    // Spinners
    const val SP_NOT_OPTION: String = "No ha seleccionado un valor válido del spinner"
    const val SP_NOT_JOB: String = "No ha seleccionado un valor válido del spinner"
}
