package com.mx.liftechnology.domain.usecase.auth

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.auth.ModelGetUserData
import com.mx.liftechnology.data.repository.auth.GetDataUserRepository
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult

class GetDataUserUseCase(
    private val preference: PreferenceUseCase,
    private val getDataUserRepository: GetDataUserRepository,
) {
    suspend operator fun invoke(remember: Boolean): ModelResult<ModelGetUserData, ModelError> {

        // 2. Ejecución de la llamada de red
        return runCatching { getDataUserRepository.executeGetData() }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        val userLogin = result.data
                        if(userLogin.isActive == true){
                            savePreferences(result.data, remember)
                            SuccessResult(userLogin)
                        }
                        else ErrorResult(NetworkModelError.NOT_ACTIVE)
                    }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
        )
    }


    /**
     * Guarda las preferencias del usuario después de un inicio de sesión exitoso.
     *
     * @param result Los datos de la respuesta de inicio de sesión.
     * @param remember Indica si se debe guardar la sesión.
     * @return `true` si las preferencias se guardaron correctamente, `false` en caso contrario.
     */
    private fun savePreferences(result: ModelGetUserData, remember: Boolean) {
        preference.setIdUser(result.userId)
        preference.setIdUserLevel(result.accessLevelId?:0)
        preference.setRememberLogin(remember)
    }
}