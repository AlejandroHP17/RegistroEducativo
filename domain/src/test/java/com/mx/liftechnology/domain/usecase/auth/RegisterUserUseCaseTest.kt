package com.mx.liftechnology.domain.usecase.auth

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.auth.RegisterUserDomain
import com.mx.liftechnology.domain.repository.auth.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterUserUseCase].
 *
 * Se valida que:
 * - Si algún campo está vacío, retorna ErrorResult USER_INCOMPLETE_DATA.
 * - Si el registro es exitoso, retorna SuccessResult(true).
 * - Los errores del repositorio se propagan correctamente.
 */
class RegisterUserUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var registerUserUseCase: RegisterUserUseCase

    @Before
    fun setup() {
        authRepository = mockk()
        registerUserUseCase = RegisterUserUseCase(authRepository)
    }

    @Test
    fun `cuando email es nulo o vacio retorna ErrorResult USER_INCOMPLETE_DATA`() = runTest {
        // When
        val resultNull: ModelResult<Boolean, ModelError> = registerUserUseCase(null, "Password123", "CODE123")
        val resultEmpty: ModelResult<Boolean, ModelError> = registerUserUseCase("", "Password123", "CODE123")

        // Then
        assertTrue(resultNull is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (resultNull as ErrorResult).error)

        assertTrue(resultEmpty is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (resultEmpty as ErrorResult).error)

        coVerify(exactly = 0) { authRepository.register(any(), any(), any()) }
    }

    @Test
    fun `cuando pass es nulo o vacio retorna ErrorResult USER_INCOMPLETE_DATA`() = runTest {
        // When
        val resultNull: ModelResult<Boolean, ModelError> = registerUserUseCase("user@test.com", null, "CODE123")
        val resultEmpty: ModelResult<Boolean, ModelError> = registerUserUseCase("user@test.com", "", "CODE123")

        // Then
        assertTrue(resultNull is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (resultNull as ErrorResult).error)

        assertTrue(resultEmpty is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (resultEmpty as ErrorResult).error)

        coVerify(exactly = 0) { authRepository.register(any(), any(), any()) }
    }

    @Test
    fun `cuando activationCode es nulo o vacio retorna ErrorResult USER_INCOMPLETE_DATA`() = runTest {
        // When
        val resultNull: ModelResult<Boolean, ModelError> = registerUserUseCase("user@test.com", "Password123", null)
        val resultEmpty: ModelResult<Boolean, ModelError> = registerUserUseCase("user@test.com", "Password123", "")

        // Then
        assertTrue(resultNull is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (resultNull as ErrorResult).error)

        assertTrue(resultEmpty is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (resultEmpty as ErrorResult).error)

        coVerify(exactly = 0) { authRepository.register(any(), any(), any()) }
    }

    @Test
    fun `cuando el registro es exitoso retorna SuccessResult true`() = runTest {
        // Given
        val registerUserDomain = RegisterUserDomain(
            email = "user@test.com",
            firstName = "Juan",
            lastName = "Pérez",
            accessLevel = 2,
            isActive = true,
            userId = 10
        )

        coEvery {
            authRepository.register(
                email = "user@test.com",
                pass = "Password123",
                activationCode = "CODE123"
            )
        } returns SuccessResult(registerUserDomain)

        // When
        val result: ModelResult<Boolean, ModelError> = registerUserUseCase("user@test.com", "Password123", "CODE123")

        // Then
        assertTrue(result is SuccessResult)
        assertEquals(true, (result as SuccessResult).data)

        coVerify(exactly = 1) {
            authRepository.register(
                email = "user@test.com",
                pass = "Password123",
                activationCode = "CODE123"
            )
        }
    }

    @Test
    fun `cuando el repositorio retorna ErrorResult se propaga el error`() = runTest {
        // Given
        val repositoryError: ModelResult<RegisterUserDomain, NetworkModelError> =
            ErrorResult(NetworkModelError.CONFLICT)

        coEvery {
            authRepository.register(any(), any(), any())
        } returns repositoryError

        // When
        val result: ModelResult<Boolean, ModelError> = registerUserUseCase("user@test.com", "Password123", "CODE123")

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.CONFLICT, (result as ErrorResult).error)
    }
}
