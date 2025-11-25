package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.data.util.LocalModelError
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.UserError

/**
 * Mapper centralizado para convertir errores de la capa de datos a errores de UI.
 * Este mapper mantiene la separación de capas, evitando que los ViewModels dependan directamente de data.util.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ErrorMapper {

    /**
     * Convierte un error de la capa de datos (data.util.ModelError) a un error de UI (UserError).
     *
     * @param error El error a convertir, puede ser LocalModelError o NetworkModelError.
     * @return Un UserError que representa cómo debe mostrarse el error al usuario.
     */
    fun mapErrorToUI(error: ModelError): UserError {
        return when (error) {
            is LocalModelError -> mapLocalErrorToUI(error)
            is NetworkModelError -> mapNetworkErrorToUI(error)
        }
    }

    /**
     * Convierte un LocalModelError a UserError.
     *
     * @param localError El error local a convertir.
     * @return Un UserError correspondiente.
     */
    private fun mapLocalErrorToUI(localError: LocalModelError): UserError {
        return when (localError) {
            LocalModelError.USER_INCOMPLETE_DATA -> UserError.SHOW_INCOMPLETE_ERROR
            LocalModelError.RESPONSE_INCOMPLETE_DATA -> UserError.SHOW_GENERIC_ERROR
            LocalModelError.CATCH -> UserError.SHOW_GENERIC_ERROR
            LocalModelError.EMPTY -> UserError.SHOW_GENERIC_ERROR
        }
    }

    /**
     * Convierte un NetworkModelError a UserError.
     *
     * @param networkError El error de red a convertir.
     * @return Un UserError correspondiente.
     */
    private fun mapNetworkErrorToUI(networkError: NetworkModelError): UserError {
        return when (networkError) {
            NetworkModelError.UNAUTHORIZED -> UserError.UNAUTHORIZED // 401

            NetworkModelError.BAD_REQUEST -> UserError.SHOW_GENERIC_ERROR //400
            NetworkModelError.CONFLICT -> UserError.SHOW_SPECIFIC_ERROR //409
            NetworkModelError.NOT_FOUND -> UserError.SHOW_SPECIFIC_ERROR // 404

            NetworkModelError.NO_INTERNET -> UserError.NO_INTERNET
            NetworkModelError.TIMEOUT -> UserError.SHOW_GENERIC_ERROR
            NetworkModelError.SERVER_ERROR -> UserError.SHOW_GENERIC_ERROR

            NetworkModelError.TOO_MANY_REQUESTS -> UserError.LOGS
            NetworkModelError.SERIALIZATION -> UserError.LOGS
            NetworkModelError.UNKNOWN -> UserError.LOGS
            NetworkModelError.NOT_ACTIVE -> UserError.USER_NOT_ACTIVE
            NetworkModelError.WITHOUT_ACCESS -> UserError.WITHOUT_ACCESS

            NetworkModelError.UNKNOWN_REGISTER -> UserError.SHOW_INCOMPLETE_ERROR // Specific
            NetworkModelError.EMPTY -> UserError.LOGS
        }
    }
}

