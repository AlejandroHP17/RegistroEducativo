package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.SpinnersWorkMethodsDomain
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterFormativeFieldsWithValidationUseCase].
 */
class RegisterFormativeFieldsWithValidationUseCaseTest {

    private lateinit var validateFieldsUseCase: ValidateFieldsFormativeFieldsUseCase
    private lateinit var registerBulkUseCase: RegisterFormativeFieldsBulkUseCase
    private lateinit var useCase: RegisterFormativeFieldsWithValidationUseCase

    @Before
    fun setup() {
        validateFieldsUseCase = mockk()
        registerBulkUseCase = mockk()
        useCase = RegisterFormativeFieldsWithValidationUseCase(validateFieldsUseCase, registerBulkUseCase)
    }

    @Test
    fun `cuando hay errores de validacion retorna resultado invalido y no registra`() = runTest {
        val error = ModelStateOutFieldText(isError = true, errorMessage = ModelCodeInputs.ET_EMPTY)
        val ok = ModelStateOutFieldText(isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)

        every { validateFieldsUseCase.validateNameCompose(null) } returns error
        every { validateFieldsUseCase.validateOptionCompose("2") } returns ok
        every { validateFieldsUseCase.validateListJobsCompose(null) } returns null

        val result = useCase(
            formativeField = null,
            options = "2",
            listAdapter = null
        )

        assertFalse(result.validationResult.isValid)
        assertTrue(result.validationResult.operationResult == null)
        coVerify(exactly = 0) { registerBulkUseCase.invoke(any(), any()) }
    }

    @Test
    fun `cuando porcentajes son invalidos retorna resultado invalido`() = runTest {
        val ok = ModelStateOutFieldText(isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        val percentError = ModelStateOutFieldText(isError = true, errorMessage = ModelCodeInputs.SP_NOT)
        val list = mutableListOf<SpinnersWorkMethodsDomain>()

        every { validateFieldsUseCase.validateNameCompose("Matematicas") } returns ok
        every { validateFieldsUseCase.validateOptionCompose("2") } returns ok
        every { validateFieldsUseCase.validateListJobsCompose(list) } returns list
        every { validateFieldsUseCase.validPercentCompose(list) } returns percentError

        val result = useCase(
            formativeField = "Matematicas",
            options = "2",
            listAdapter = list
        )

        assertFalse(result.validationResult.isValid)
        assertTrue(result.validationResult.operationResult == null)
        coVerify(exactly = 0) { registerBulkUseCase.invoke(any(), any()) }
    }

    @Test
    fun `cuando validaciones son correctas registra y retorna resultado valido`() = runTest {
        val ok = ModelStateOutFieldText(isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        val list = mutableListOf<SpinnersWorkMethodsDomain>()

        every { validateFieldsUseCase.validateNameCompose("Matematicas") } returns ok
        every { validateFieldsUseCase.validateOptionCompose("2") } returns ok
        every { validateFieldsUseCase.validateListJobsCompose(list) } returns list
        every { validateFieldsUseCase.validPercentCompose(list) } returns ok

        val domain = FormativeFieldDomain(id = 1, name = "Matematicas", isActive = true)
        val opResult: ModelResult<FormativeFieldDomain, ModelError> = SuccessResult(domain)
        coEvery { registerBulkUseCase.invoke(list, "Matematicas") } returns opResult

        val result = useCase(
            formativeField = "Matematicas",
            options = "2",
            listAdapter = list
        )

        assertTrue(result.validationResult.isValid)
        assertNotNull(result.validationResult.operationResult)
        assertTrue(result.validationResult.operationResult is SuccessResult<*>)
    }
}
