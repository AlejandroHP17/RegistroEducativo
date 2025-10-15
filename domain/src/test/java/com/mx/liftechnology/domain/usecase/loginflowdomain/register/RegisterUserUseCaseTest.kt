package com.mx.liftechnology.domain.usecase.loginflowdomain.register

import com.mx.liftechnology.data.repository.flowLogin.register.RegisterUserRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.SuccessState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterUserUseCase].
 * Verifica el comportamiento del caso de uso de registro de usuario en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserUseCaseTest {

    private lateinit var registerUserUseCase: RegisterUserUseCase
    private val repository: RegisterUserRepository = mockk()

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        registerUserUseCase = RegisterUserUseCase(repository)
    }

    /**
     * Test para el flujo de registro exitoso.
     */
    @Test
    fun `invoke con datos validos debe devolver SuccessState`() = runBlocking {
        // Preparamos el mock
        coEvery { repository.executeRegisterUser(any()) } returns ResultSuccess(emptyList())

        // Ejecutamos el caso de uso
        val result = registerUserUseCase.invoke("test@test.com", "password", "12345")

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }

    /**
     * Test para el flujo de registro con error de usuario.
     */
    @Test
    fun `invoke con datos invalidos debe devolver ErrorUserState`() = runBlocking {
        // Preparamos el mock
        coEvery { repository.executeRegisterUser(any()) } returns ResultError(FailureService.BadRequest)

        // Ejecutamos el caso de uso
        val result = registerUserUseCase.invoke("wrong@test.com", "wrong", "wrong")

        // Verificamos el resultado
        assertTrue(result is ErrorUserState)
    }
}