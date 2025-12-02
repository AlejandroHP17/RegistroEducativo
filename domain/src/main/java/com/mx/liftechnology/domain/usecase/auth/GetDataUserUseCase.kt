package com.mx.liftechnology.domain.usecase.auth

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.auth.GetDataUserRepository
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.auth.UserDomain
import com.mx.liftechnology.domain.model.auth.toDomain

/**
 * Caso de uso para obtener los datos del usuario autenticado.
 * Encapsula la lógica de negocio para recuperar los datos del usuario, validar que esté activo
 * y guardar las preferencias de sesión.
 *
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 * @property getDataUserRepository El repositorio para obtener los datos del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetDataUserUseCase(
    private val preference: PreferenceUseCase,
    private val getDataUserRepository: GetDataUserRepository,
) {
    /**
     * Ejecuta el proceso de obtención de datos del usuario.
     * Obtiene los datos del usuario desde el repositorio, valida que el usuario esté activo
     * y guarda las preferencias de sesión si la operación es exitosa.
     *
     * @param remember Indica si se debe recordar la sesión del usuario para futuros inicios de sesión.
     * @return Un [ModelResult] que contiene los datos del usuario ([UserDomain]) en caso de éxito,
     * o un estado de error específico en caso de fallo.
     *
     * Posibles errores:
     * - [NetworkModelError.NOT_ACTIVE] si el usuario no está activo
     * - [ModelError] de red si hay problemas de conexión
     * - [ModelError] de autenticación si no se pueden obtener los datos del usuario
     *
     * @example
     * ```
     * val result = getDataUserUseCase(remember = true)
     * when (result) {
     *     is SuccessResult -> {
     *         val user = result.data
     *         println("Usuario: ${user.name}")
     *     }
     *     is ErrorResult -> {
     *         if (result.error == NetworkModelError.NOT_ACTIVE) {
     *             println("Usuario inactivo")
     *         }
     *     }
     * }
     * ```
     */
    suspend operator fun invoke(remember: Boolean): ModelResult<UserDomain, ModelError> {
        val result = getDataUserRepository.getData()
        return when (result) {
            is SuccessResult -> {
                val userData = result.data
                if (userData.isActive == true) {
                    savePreferences(userData, remember)
                    SuccessResult(userData.toDomain())
                } else {
                    ErrorResult(NetworkModelError.NOT_ACTIVE)
                }
            }
            is ErrorResult -> result
        }
    }

    /**
     * Guarda las preferencias del usuario después de un inicio de sesión exitoso.
     *
     * @param userData Los datos del usuario de la capa de datos (necesarios para guardar preferencias).
     * @param remember Indica si se debe guardar la sesión.
     */
    private fun savePreferences(userData: com.mx.liftechnology.data.model.auth.ModelGetUserData, remember: Boolean) {
        preference.setIdUser(userData.userId)
        preference.setIdUserLevel(userData.accessLevelId ?: 0)
        preference.setRememberLogin(remember)
    }
}