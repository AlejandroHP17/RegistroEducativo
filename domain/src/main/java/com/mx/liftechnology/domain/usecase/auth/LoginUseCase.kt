package com.mx.liftechnology.domain.usecase.auth

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.device.DeviceIdHelper
import com.mx.liftechnology.core.util.location.LocationHelper
import com.mx.liftechnology.core.util.location.LocationResult
import com.mx.liftechnology.data.model.auth.ModelGetUserData
import com.mx.liftechnology.data.repository.auth.LoginRepository
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalModelError
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult

/**
 * Caso de uso para gestionar el inicio de sesión de un usuario.
 * Encapsula la lógica de negocio para validar credenciales, obtener la ubicación, interactuar con el repositorio y guardar las preferencias de sesión.
 *
 * @property repositoryLogin El repositorio para las operaciones de inicio de sesión.
 * @property locationHelper Utilidad para obtener la ubicación actual del dispositivo.
 * @property deviceIdHelper Utilidad para obtener el identificador único del dispositivo de forma segura.
 * @property preference Caso de uso para la gestión de las preferencias de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginUseCase(
    private val repositoryLogin: LoginRepository,
    private val locationHelper: LocationHelper,
    private val deviceIdHelper: DeviceIdHelper,
    private val preference: PreferenceUseCase,
    private val getDataUserUseCase: GetDataUserUseCase
) {

    /**
     * Ejecuta el proceso de inicio de sesión.
     * Primero, valida las entradas locales. Si son válidas, procede a hacer la llamada de red.
     *
     * @param email El correo electrónico del usuario.
     * @param pass La contraseña del usuario.
     * @param remember Indica si la sesión del usuario debe ser recordada.
     * @return Un [ModelResult] que puede ser:
     * - [SuccessResult<UserLogin>] si el inicio de sesión es exitoso.
     * - [ErrorResult<LocalModelError>] si hay un error de validación local (campos vacíos).
     * - [ErrorResult<NetworkModelError>] si hay un error de red o del servidor.
     */
    suspend operator fun invoke (email: String?, pass: String?, remember: Boolean = false): ModelResult<ModelGetUserData, ModelError> {
        // 1. Validación de Lógica de Negocio (Local)
        if (email.isNullOrBlank() || pass.isNullOrBlank()) {
            return ErrorResult(LocalModelError.USER_INCOMPLETE_DATA)
        }

        // Obtener ubicación usando LocationResult
        val locationResult = locationHelper.getCurrentLocation()
        val (latitude, longitude) = when (locationResult) {
            is LocationResult.Success -> {
                locationResult.location.latitude to locationResult.location.longitude
            }
            is LocationResult.Error -> {
                // Si no se puede obtener la ubicación, usar valores por defecto
                0.0 to 0.0
            }
        }

        // 2. Obtener identificador único del dispositivo de forma segura
        val deviceId = deviceIdHelper.getDeviceId()

        // 3. Ejecución de la llamada de red
        return runCatching { repositoryLogin.executeLogin(
            email = email.lowercase().trim(),
            password = pass,
            latitude = latitude,
            longitude = longitude,
            imei = deviceId
        ) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        preference.setAccessToken(result.data.accessToken)
                        preference.setRefreshToken(result.data.refreshToken)
                        getDataUserUseCase.invoke(remember)
                    }
                    is ErrorResult -> {
                        ErrorResult(result.error) // Mapea el error de la capa de datos al dominio
                    }
                }
            },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) } // Captura excepciones como problemas de conectividad
        )
    }
}