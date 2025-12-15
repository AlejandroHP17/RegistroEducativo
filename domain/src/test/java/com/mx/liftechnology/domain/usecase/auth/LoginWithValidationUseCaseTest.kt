package com.mx.liftechnology.domain.usecase.auth

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.auth.UserDomain
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.usecase.share.ValidateAuthFieldsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [LoginWithValidationUseCase].
 *
 * Se valida que:
 * - Cuando hay errores de validación, no se ejecuta el [LoginUseCase] y se retorna un [ModelValidationResult] inválido.
 * - Cuando las validaciones pasan, se ejecuta el [LoginUseCase] y el resultado se propaga correctamente.
 */
class LoginWithValidationUseCaseTest {

    private lateinit var validateAuthFieldsUseCase: ValidateAuthFieldsUseCase
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var loginWithValidationUseCase: LoginWithValidationUseCase

    @Before
    fun setup() {
        validateAuthFieldsUseCase = mockk()
        loginUseCase = mockk()
        loginWithValidationUseCase = LoginWithValidationUseCase(
            validateFieldsUseCase = validateAuthFieldsUseCase,
            loginUseCase = loginUseCase
        )
    }

    @Test
    fun `cuando hay errores de validacion no se ejecuta el login y el resultado es invalido`() = runTest {
        // Given
        val invalidEmailState = ModelStateOutFieldText(
            valueText = "",
            isError = true,
            errorMessage = ModelCodeInputs.ET_EMPTY
        )
        val validPassState = ModelStateOutFieldText(
            valueText = "Password123",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )

        every { validateAuthFieldsUseCase.validateEmailCompose(null) } returns invalidEmailState
        every { validateAuthFieldsUseCase.validatePassCompose("Password123") } returns validPassState

        // When
        val result: ModelValidationResult<UserDomain> = loginWithValidationUseCase.invoke(null, "Password123", false)

        // Then
        assertFalse(result.isValid)
        assertNull(result.operationResult)

        assertEquals(2, result.validationStates.size)
        val emailState = result.validationStates["email"]
        val passState = result.validationStates["pass"]

        assertNotNull(emailState)
        assertTrue(emailState!!.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, emailState.errorMessage)

        assertNotNull(passState)
        assertFalse(passState!!.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, passState.errorMessage)

        coVerify(exactly = 0) { loginUseCase.invoke(any(), any(), any()) }
    }

    @Test
    fun `cuando las validaciones son correctas se ejecuta el login y el resultado es valido`() = runTest {
        // Given
        val validEmailState = ModelStateOutFieldText(
            valueText = "user@test.com",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validPassState = ModelStateOutFieldText(
            valueText = "Password123",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )

        every { validateAuthFieldsUseCase.validateEmailCompose("user@test.com") } returns validEmailState
        every { validateAuthFieldsUseCase.validatePassCompose("Password123") } returns validPassState

        val userDomain = UserDomain(
            email = "user@test.com",
            name = "User",
            lastName = "Test",
            phone = null,
            isActive = true,
            userId = 10,
            accessLevelId = 1
        )

        val loginResult: ModelResult<UserDomain, ModelError> = SuccessResult(userDomain)

        coEvery { loginUseCase.invoke("user@test.com", "Password123", true) } returns loginResult

        // When
        val result: ModelValidationResult<UserDomain> = loginWithValidationUseCase.invoke("user@test.com", "Password123", true)

        // Then
        assertTrue(result.isValid)
        assertNotNull(result.operationResult)
        assertTrue(result.operationResult is SuccessResult<*>)
        assertEquals(userDomain, (result.operationResult as SuccessResult).data)

        assertEquals(2, result.validationStates.size)
        val emailState = result.validationStates["email"]
        val passState = result.validationStates["pass"]

        assertNotNull(emailState)
        assertFalse(emailState!!.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, emailState.errorMessage)

        assertNotNull(passState)
        assertFalse(passState!!.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, passState.errorMessage)

        coVerify(exactly = 1) { loginUseCase.invoke("user@test.com", "Password123", true) }
    }

    @Test
    fun `cuando login devuelve ErrorResult el resultado valido contiene el error`() = runTest {
        // Given
        val validEmailState = ModelStateOutFieldText(
            valueText = "user@test.com",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validPassState = ModelStateOutFieldText(
            valueText = "Password123",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )

        every { validateAuthFieldsUseCase.validateEmailCompose("user@test.com") } returns validEmailState
        every { validateAuthFieldsUseCase.validatePassCompose("Password123") } returns validPassState

        val errorResult: ModelResult<UserDomain, ModelError> = ErrorResult(mockk())

        coEvery { loginUseCase.invoke("user@test.com", "Password123", false) } returns errorResult

        // When
        val result: ModelValidationResult<UserDomain> = loginWithValidationUseCase.invoke("user@test.com", "Password123", false)

        // Then
        assertTrue(result.isValid)
        assertNotNull(result.operationResult)
        assertTrue(result.operationResult is ErrorResult<*>)

        coVerify(exactly = 1) { loginUseCase.invoke("user@test.com", "Password123", false) }
    }
}
