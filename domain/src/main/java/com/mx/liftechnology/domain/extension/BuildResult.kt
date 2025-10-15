/**
 * @file Proporciona una función de extensión para construir un resultado de servicio a partir de un objeto.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.extension

import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess

/**
 * Construye un [ResultService] a partir del objeto actual.
 * Si el objeto no es nulo, devuelve un [ResultSuccess]; de lo contrario, devuelve un [ResultError] con un fallo de servidor.
 *
 * @return Un [ResultService] que encapsula el objeto actual o un error.
 */
fun <T> T.buildResult(): ResultService<T?, FailureService> {
    return this?.let { ResultSuccess(it) } ?: ResultError(FailureService.ServerError)
}