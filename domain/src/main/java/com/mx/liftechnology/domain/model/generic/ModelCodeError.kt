package com.mx.liftechnology.domain.model.generic

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
}
