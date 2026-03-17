package com.mx.liftechnology.domain.usecase.auth

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.device.DeviceIdHelper
import com.mx.liftechnology.core.util.location.LocationHelper
import com.mx.liftechnology.core.util.location.LocationResult
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.auth.UserDomain
import com.mx.liftechnology.domain.repository.auth.AuthRepository

/**
 * Caso de uso para gestionar el inicio de sesión de un usuario.
 * Encapsula la lógica de negocio para validar credenciales, obtener la ubicación, interactuar con el repositorio y guardar las preferencias de sesión.
 *
 * @property authRepository El repositorio para las operaciones de autenticación.
 * @property locationHelper Utilidad para obtener la ubicación actual del dispositivo.
 * @property deviceIdHelper Utilidad para obtener el identificador único del dispositivo de forma segura.
 * @property preference Caso de uso para la gestión de las preferencias de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginUseCase(
    private val authRepository: AuthRepository,
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
     * - [SuccessResult<UserDomain>] si el inicio de sesión es exitoso.
     * - [ErrorResult<LocalModelError>] si hay un error de validación local (campos vacíos).
     * - [ErrorResult<NetworkModelError>] si hay un error de red o del servidor.
     */
    suspend operator fun invoke (email: String?, pass: String?, remember: Boolean = false): ModelResult<UserDomain, ModelError> {
        // 1. Validación de Lógica de Negocio (Local)
        if (email.isNullOrBlank() || pass.isNullOrBlank()) {
            return ErrorResult(LocalModelError.USER_INCOMPLETE_DATA)
        }

        // Obtener ubicación usando LocationResult
        val locationResult = locationHelper.getCurrentLocation()
        val (latitude, longitude) = when (locationResult) {
            is LocationResult.Success -> {
                19.4596 to -99.1112
                //locationResult.location.latitude to locationResult.location.longitude
            }
            is LocationResult.Error -> {
                // Si no se puede obtener la ubicación, usar valores por defecto
                19.4596 to -99.1112
            }
        }

        // 2. Obtener identificador único del dispositivo de forma segura
        val deviceId = deviceIdHelper.getDeviceId()

        // 3. Ejecución de la llamada de red
        val result = authRepository.login(
            email = email.lowercase().trim(),
            password = pass,
            latitude = latitude,
            longitude = longitude,
            imei = deviceId
        )
        return when (result) {
            is SuccessResult -> {
                preference.setAccessToken(result.data.accessToken)
                preference.setRefreshToken(result.data.refreshToken)
                getDataUserUseCase.invoke(remember)
            }
            is ErrorResult -> result
        }
    }
}