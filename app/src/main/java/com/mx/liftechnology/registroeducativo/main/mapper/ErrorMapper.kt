package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.NetworkError
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
     * Convierte un error de la capa de datos (data.util.Error) a un error de UI (UserError).
     *
     * @param error El error a convertir, puede ser LocalError o NetworkError.
     * @return Un UserError que representa cómo debe mostrarse el error al usuario.
     */
    fun mapErrorToUI(error: Error): UserError {
        return when (error) {
            is LocalError -> mapLocalErrorToUI(error)
            is NetworkError -> mapNetworkErrorToUI(error)
        }
    }

    /**
     * Convierte un LocalError a UserError.
     *
     * @param localError El error local a convertir.
     * @return Un UserError correspondiente.
     */
    private fun mapLocalErrorToUI(localError: LocalError): UserError {
        return when (localError) {
            LocalError.USER_INCOMPLETE_DATA -> UserError.SHOW_INCOMPLETE_ERROR
            LocalError.RESPONSE_INCOMPLETE_DATA -> UserError.SHOW_GENERIC_ERROR
            LocalError.CATCH -> UserError.SHOW_GENERIC_ERROR
            LocalError.EMPTY -> UserError.SHOW_GENERIC_ERROR
        }
    }

    /**
     * Convierte un NetworkError a UserError.
     *
     * @param networkError El error de red a convertir.
     * @return Un UserError correspondiente.
     */
    private fun mapNetworkErrorToUI(networkError: NetworkError): UserError {
        return when (networkError) {
            NetworkError.UNAUTHORIZED -> UserError.UNAUTHORIZED // 401

            NetworkError.BAD_REQUEST -> UserError.SHOW_SPECIFIC_ERROR //400
            NetworkError.CONFLICT -> UserError.SHOW_SPECIFIC_ERROR //409
            NetworkError.NOT_FOUND -> UserError.SHOW_SPECIFIC_ERROR // 404

            NetworkError.NO_INTERNET -> UserError.SHOW_GENERIC_ERROR
            NetworkError.TIMEOUT -> UserError.SHOW_GENERIC_ERROR
            NetworkError.SERVER_ERROR -> UserError.SHOW_GENERIC_ERROR

            NetworkError.TOO_MANY_REQUESTS -> UserError.LOGS
            NetworkError.SERIALIZATION -> UserError.LOGS
            NetworkError.UNKNOWN -> UserError.LOGS

            // Registro Usuario
            NetworkError.UNKNOWN_REGISTER -> UserError.SHOW_INCOMPLETE_ERROR // Specific
            NetworkError.EMPTY -> UserError.LOGS
        }
    }
}

