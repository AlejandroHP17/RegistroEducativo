package com.mx.liftechnology.domain.usecase.auth

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.auth.UserDomain
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
 * Tests para [GetDataUserUseCase].
 *
 * Se valida que:
 * - Si el usuario está activo, se guardan las preferencias y se retorna SuccessResult.
 * - Si el usuario no está activo, se retorna ErrorResult con NOT_ACTIVE.
 * - Los errores del repositorio se propagan correctamente.
 */
class GetDataUserUseCaseTest {

    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var authRepository: AuthRepository
    private lateinit var getDataUserUseCase: GetDataUserUseCase

    @Before
    fun setup() {
        preferenceUseCase = mockk(relaxed = true)
        authRepository = mockk()
        getDataUserUseCase = GetDataUserUseCase(
            preference = preferenceUseCase,
            authRepository = authRepository
        )
    }

    @Test
    fun `cuando el usuario esta activo guarda preferencias y retorna SuccessResult`() = runTest {
        // Given
        val userDomain = UserDomain(
            email = "user@test.com",
            name = "Juan",
            lastName = "Pérez",
            phone = "1234567890",
            isActive = true,
            userId = 10,
            accessLevelId = 2
        )

        val remember = true

        coEvery { authRepository.getData() } returns SuccessResult(userDomain)

        // When
        val result: ModelResult<UserDomain, ModelError> = getDataUserUseCase.invoke(remember)

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(userDomain.email, successResult.data.email)
        assertEquals(userDomain.name, successResult.data.name)
        assertEquals(userDomain.userId, successResult.data.userId)

        coVerify(exactly = 1) { preferenceUseCase.setIdUser(10) }
        coVerify(exactly = 1) { preferenceUseCase.setIdUserLevel(2) }
        coVerify(exactly = 1) { preferenceUseCase.setRememberLogin(true) }
    }

    @Test
    fun `cuando el usuario no esta activo retorna ErrorResult NOT_ACTIVE`() = runTest {
        // Given
        val userDomain = UserDomain(
            email = "user@test.com",
            name = "Juan",
            lastName = "Pérez",
            phone = "1234567890",
            isActive = false,
            userId = 10,
            accessLevelId = 2
        )

        coEvery { authRepository.getData() } returns SuccessResult(userDomain)

        // When
        val result: ModelResult<UserDomain, ModelError> = getDataUserUseCase.invoke(false)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_ACTIVE, (result as ErrorResult).error)

        coVerify(exactly = 0) { preferenceUseCase.setIdUser(any()) }
        coVerify(exactly = 0) { preferenceUseCase.setIdUserLevel(any()) }
        coVerify(exactly = 0) { preferenceUseCase.setRememberLogin(any()) }
    }

    @Test
    fun `cuando isActive es null retorna ErrorResult NOT_ACTIVE`() = runTest {
        // Given
        val userDomain = UserDomain(
            email = "user@test.com",
            name = "Juan",
            lastName = "Pérez",
            phone = "1234567890",
            isActive = null,
            userId = 10,
            accessLevelId = 2
        )

        coEvery { authRepository.getData() } returns SuccessResult(userDomain)

        // When
        val result: ModelResult<UserDomain, ModelError> = getDataUserUseCase.invoke(false)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_ACTIVE, (result as ErrorResult).error)
    }

    @Test
    fun `cuando el repositorio retorna ErrorResult se propaga el error`() = runTest {
        // Given
        val repositoryError: ModelResult<UserDomain, NetworkModelError> =
            ErrorResult(NetworkModelError.UNAUTHORIZED)

        coEvery { authRepository.getData() } returns repositoryError

        // When
        val result: ModelResult<UserDomain, ModelError> = getDataUserUseCase.invoke(false)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNAUTHORIZED, (result as ErrorResult).error)

        coVerify(exactly = 0) { preferenceUseCase.setIdUser(any()) }
    }

    @Test
    fun `cuando accessLevelId es null se guarda 0 como valor por defecto`() = runTest {
        // Given
        val userDomain = UserDomain(
            email = "user@test.com",
            name = "Juan",
            lastName = "Pérez",
            phone = "1234567890",
            isActive = true,
            userId = 10,
            accessLevelId = null
        )

        coEvery { authRepository.getData() } returns SuccessResult(userDomain)

        // When
        getDataUserUseCase.invoke(false)

        // Then
        coVerify(exactly = 1) { preferenceUseCase.setIdUserLevel(0) }
    }
}
