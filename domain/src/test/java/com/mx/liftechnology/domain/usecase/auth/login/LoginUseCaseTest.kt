package com.mx.liftechnology.domain.usecase.auth.login

import android.location.Location
import com.mx.liftechnology.core.network.apiCall.auth.ResponseLogin
import com.mx.liftechnology.core.network.apiCall.auth.ResponseDataUser
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.LocationHelper
import com.mx.liftechnology.data.repository.auth.LoginRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.usecase.auth.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [com.mx.liftechnology.domain.usecase.auth.LoginUseCase].
 * Verifica el comportamiento del caso de uso de inicio de sesión en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private val repository: LoginRepository = mockk()
    private val locationHelper: LocationHelper = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(repository, locationHelper, preferenceUseCase)
    }

    /**
     * Test para el flujo de inicio de sesión exitoso.
     */
    @Test
    fun `invoke con credenciales validas debe devolver SuccessState`() = runBlocking {
        // Preparamos los mocks
        val mockLocation = mockk<Location>(relaxed = true)
        val mockUser = ResponseDataUser("Test", "User", "", "test@test.com", 1, 1, null, "profesor")
        val mockResponse = ResponseLogin("token", 3600, "Bearer", mockUser)

        coEvery { locationHelper.getCurrentLocation() } returns mockLocation
        coEvery { repository.executeLogin(any()) } returns ResultSuccess(mockResponse)

        // Ejecutamos el caso de uso
        val result = loginUseCase.invoke("test@test.com", "password", true)

        // Verificamos el resultado
        assertTrue(result is SuccessResult)
    }

    /**
     * Test para el flujo de inicio de sesión con credenciales incorrectas.
     */
    @Test
    fun `invoke con credenciales invalidas debe devolver ErrorUserState`() = runBlocking {
        // Preparamos los mocks
        coEvery { locationHelper.getCurrentLocation() } returns null
        coEvery { repository.executeLogin(any()) } returns ResultError(FailureService.BadRequest)

        // Ejecutamos el caso de uso
        val result = loginUseCase.invoke("wrong@test.com", "wrong", false)

        // Verificamos el resultado
        assertTrue(result is ErrorUserResult)
    }
}