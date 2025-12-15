package com.mx.liftechnology.domain.usecase.auth

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
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
 * Tests para [RegisterUserWithValidationUseCase].
 *
 * Se valida que:
 * - Cuando hay errores de validación, no se ejecuta el registro y se retorna un resultado inválido.
 * - Cuando las validaciones pasan, se ejecuta el registro y el resultado se propaga correctamente.
 */
class RegisterUserWithValidationUseCaseTest {

    private lateinit var validateAuthFieldsUseCase: ValidateAuthFieldsUseCase
    private lateinit var registerUserUseCase: RegisterUserUseCase
    private lateinit var registerUserWithValidationUseCase: RegisterUserWithValidationUseCase

    @Before
    fun setup() {
        validateAuthFieldsUseCase = mockk()
        registerUserUseCase = mockk()
        registerUserWithValidationUseCase = RegisterUserWithValidationUseCase(
            validateFieldsUseCase = validateAuthFieldsUseCase,
            registerUserUseCase = registerUserUseCase
        )
    }

    @Test
    fun `cuando hay errores de validacion no se ejecuta el registro y el resultado es invalido`() = runTest {
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
        val validRepeatPassState = ModelStateOutFieldText(
            valueText = "Password123",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validCodeState = ModelStateOutFieldText(
            valueText = "CODE123",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )

        every { validateAuthFieldsUseCase.validateEmailCompose(null) } returns invalidEmailState
        every { validateAuthFieldsUseCase.validatePassRegisterCompose("Password123") } returns validPassState
        every { validateAuthFieldsUseCase.validateRepeatPassCompose("Password123", "Password123") } returns validRepeatPassState
        every { validateAuthFieldsUseCase.validateCodeCompose("CODE123") } returns validCodeState

        // When
        val result: ModelValidationResult<Boolean> = registerUserWithValidationUseCase.invoke(
            email = null,
            pass = "Password123",
            repeatPass = "Password123",
            activationCode = "CODE123"
        )

        // Then
        assertFalse(result.isValid)
        assertNull(result.operationResult)

        assertEquals(4, result.validationStates.size)
        assertTrue(result.validationStates["email"]!!.isError)
        assertFalse(result.validationStates["pass"]!!.isError)
        assertFalse(result.validationStates["repeatPass"]!!.isError)
        assertFalse(result.validationStates["code"]!!.isError)

        coVerify(exactly = 0) { registerUserUseCase.invoke(any(), any(), any()) }
    }

    @Test
    fun `cuando las validaciones son correctas se ejecuta el registro y el resultado es valido`() = runTest {
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
        val validRepeatPassState = ModelStateOutFieldText(
            valueText = "Password123",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validCodeState = ModelStateOutFieldText(
            valueText = "CODE123",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )

        every { validateAuthFieldsUseCase.validateEmailCompose("user@test.com") } returns validEmailState
        every { validateAuthFieldsUseCase.validatePassRegisterCompose("Password123") } returns validPassState
        every { validateAuthFieldsUseCase.validateRepeatPassCompose("Password123", "Password123") } returns validRepeatPassState
        every { validateAuthFieldsUseCase.validateCodeCompose("CODE123") } returns validCodeState

        val registerResult: ModelResult<Boolean, ModelError> = SuccessResult(true)

        coEvery { registerUserUseCase.invoke("user@test.com", "Password123", "CODE123") } returns registerResult

        // When
        val result: ModelValidationResult<Boolean> = registerUserWithValidationUseCase.invoke(
            email = "user@test.com",
            pass = "Password123",
            repeatPass = "Password123",
            activationCode = "CODE123"
        )

        // Then
        assertTrue(result.isValid)
        assertNotNull(result.operationResult)
        assertTrue(result.operationResult is SuccessResult<*>)
        assertEquals(true, (result.operationResult as SuccessResult).data)

        assertEquals(4, result.validationStates.size)
        assertFalse(result.validationStates["email"]!!.isError)
        assertFalse(result.validationStates["pass"]!!.isError)
        assertFalse(result.validationStates["repeatPass"]!!.isError)
        assertFalse(result.validationStates["code"]!!.isError)

        coVerify(exactly = 1) { registerUserUseCase.invoke("user@test.com", "Password123", "CODE123") }
    }

    @Test
    fun `cuando registro devuelve ErrorResult el resultado valido contiene el error`() = runTest {
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
        val validRepeatPassState = ModelStateOutFieldText(
            valueText = "Password123",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validCodeState = ModelStateOutFieldText(
            valueText = "CODE123",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )

        every { validateAuthFieldsUseCase.validateEmailCompose("user@test.com") } returns validEmailState
        every { validateAuthFieldsUseCase.validatePassRegisterCompose("Password123") } returns validPassState
        every { validateAuthFieldsUseCase.validateRepeatPassCompose("Password123", "Password123") } returns validRepeatPassState
        every { validateAuthFieldsUseCase.validateCodeCompose("CODE123") } returns validCodeState

        val errorResult: ModelResult<Boolean, ModelError> = ErrorResult(NetworkModelError.CONFLICT)

        coEvery { registerUserUseCase.invoke("user@test.com", "Password123", "CODE123") } returns errorResult

        // When
        val result: ModelValidationResult<Boolean> = registerUserWithValidationUseCase.invoke(
            email = "user@test.com",
            pass = "Password123",
            repeatPass = "Password123",
            activationCode = "CODE123"
        )

        // Then
        assertTrue(result.isValid)
        assertNotNull(result.operationResult)
        assertTrue(result.operationResult is ErrorResult<*>)
        assertEquals(NetworkModelError.CONFLICT, (result.operationResult as ErrorResult).error)

        coVerify(exactly = 1) { registerUserUseCase.invoke("user@test.com", "Password123", "CODE123") }
    }
}
