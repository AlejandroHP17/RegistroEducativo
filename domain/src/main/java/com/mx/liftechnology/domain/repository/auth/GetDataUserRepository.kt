/**
 * @file Define la interfaz del repositorio para obtener los datos del usuario autenticado.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.repository.auth

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.auth.UserDomain

/**
 * Interfaz del repositorio para obtener los datos del usuario autenticado.
 * Define el contrato para ejecutar la lógica de obtención de datos del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetDataUserRepository{
    /**
     * Obtiene los datos del usuario autenticado desde el servidor.
     *
     * @return Un [ModelResult] que contiene los datos del usuario en caso de éxito,
     *         o un [NetworkModelError] en caso de fallo.
     */
    suspend fun getData(): ModelResult<UserDomain, NetworkModelError>
}
