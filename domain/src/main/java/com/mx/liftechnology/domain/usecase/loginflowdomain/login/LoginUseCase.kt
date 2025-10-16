package com.mx.liftechnology.domain.usecase.loginflowdomain.login

import android.os.Build
import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestLogin
import com.mx.liftechnology.core.network.apiCall.flowLogin.ResponseLogin
import com.mx.liftechnology.core.network.apiCall.flowLogin.UserLogin
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.LocationHelper
import com.mx.liftechnology.data.repository.flowLogin.login.LoginRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

/**
 * Caso de uso para gestionar el inicio de sesión de un usuario.
 * Encapsula la lógica de negocio para validar credenciales, obtener la ubicación, interactuar con el repositorio y guardar las preferencias de sesión.
 *
 * @property repositoryLogin El repositorio para las operaciones de inicio de sesión.
 * @property locationHelper Utilidad para obtener la ubicación actual del dispositivo.
 * @property preference Caso de uso para la gestión de las preferencias de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginUseCase(
    private val repositoryLogin: LoginRepository,
    private val locationHelper: LocationHelper,
    private val preference: PreferenceUseCase
) {

    /**
     * Ejecuta el proceso de inicio de sesión.
     *
     * @param email El correo electrónico del usuario.
     * @param pass La contraseña del usuario.
     * @param remember Indica si la sesión del usuario debe ser recordada.
     * @return Un [ModelState] que representa el resultado del intento de inicio de sesión, ya sea un éxito con los datos del usuario o un error.
     */
    suspend operator fun invoke (email: String?, pass: String?, remember: Boolean = false): ModelState<UserLogin?, String> {
        val location = locationHelper.getCurrentLocation()
        val latitude = location?.latitude
        val longitude = location?.longitude

        if (email.isNullOrEmpty() || pass.isNullOrEmpty()) return ErrorState(ModelCodeError.ERROR_VALIDATION_LOGIN)

        val request = RequestLogin(
            email = email.lowercase(),
            password = pass,
            latitude = latitude?.toString().orEmpty(),
            longitude = longitude?.toString().orEmpty(),
            imei = Build.FINGERPRINT + Build.ID
        )

        return runCatching { repositoryLogin.executeLogin(request) }.fold(
            onSuccess = { result ->
                when (result){
                    is ResultSuccess -> {
                        result.data.accessToken?.let {
                            if(savePreferences(result.data, remember)) SuccessState(result.data.userLogin)
                            else ErrorState(ModelCodeError.ERROR_CRITICAL)
                        }?:ErrorState(ModelCodeError.ERROR_CRITICAL)
                    }
                    is ResultError -> {
                        handleResponse(result.error)
                    }
                }
            },
            onFailure = { ErrorState(ModelCodeError.ERROR_CRITICAL) }
        )
    }

    /**
     * Guarda las preferencias del usuario después de un inicio de sesión exitoso.
     *
     * @param result Los datos de la respuesta de inicio de sesión.
     * @param remember Indica si se debe guardar la sesión.
     * @return `true` si las preferencias se guardaron correctamente, `false` en caso contrario.
     */
    private fun savePreferences(result: ResponseLogin?, remember:Boolean): Boolean {
        return result?.userLogin?.let { data ->
            preference.savePreferenceString(ModelPreference.ACCESS_TOKEN, result.accessToken)
            preference.savePreferenceInt(ModelPreference.ID_USER, data.userId)
            preference.savePreferenceInt(ModelPreference.ID_ROLE,
                if (data.teacherId == null) data.studentId
                else data.teacherId
            )
            preference.savePreferenceString(ModelPreference.USER_ROLE, data.role)
            preference.savePreferenceBoolean(ModelPreference.LOGIN, remember)
            true
        }?: false
    }

    /**
     * Maneja las respuestas de error del repositorio de inicio de sesión.
     *
     * @param error El objeto [FailureService] que representa el error.
     * @return Un [ModelState] que representa el error específico.
     */
    private fun handleResponse(error: FailureService): ModelState<UserLogin?, String> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_LOGIN)
            is FailureService.Unauthorized -> ErrorState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_LOGIN)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

}
