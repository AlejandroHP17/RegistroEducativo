package com.mx.liftechnology.domain.usecase.partial

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialDomain
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsRegisterPartialUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterPartialWithValidationUseCase].
 */
class RegisterPartialWithValidationUseCaseTest {

    private lateinit var validateFieldsUseCase: ValidateFieldsRegisterPartialUseCase
    private lateinit var registerListPartialUseCase: RegisterListPartialUseCase
    private lateinit var useCase: RegisterPartialWithValidationUseCase

    @Before
    fun setup() {
        validateFieldsUseCase = mockk()
        registerListPartialUseCase = mockk()
        useCase = RegisterPartialWithValidationUseCase(validateFieldsUseCase, registerListPartialUseCase)
    }

    @Test
    fun `cuando hay errores de validacion retorna resultado invalido y no registra`() = runTest {
        val errorPeriod = ModelStateOutFieldText(isError = true, errorMessage = ModelCodeInputs.ET_EMPTY)
        val adapter = emptyList<DatePeriodDomain>()
        every { validateFieldsUseCase.validatePeriod("0") } returns errorPeriod
        every { validateFieldsUseCase.validateAdapter(adapter) } returns adapter
        every { validateFieldsUseCase.validateAdapterError(adapter) } returns ModelStateOutFieldText(isError = false)

        val result = useCase("0", adapter)

        assertFalse(result.validationResult.isValid)
        assertEquals(adapter, result.updatedListCalendar)
        coVerify(exactly = 0) { registerListPartialUseCase.invoke(any()) }
    }

    @Test
    fun `cuando validaciones son correctas registra y retorna resultado valido`() = runTest {
        val ok = ModelStateOutFieldText(isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        val adapter = listOf(DatePeriodDomain(0, ModelStateOutFieldText(valueText = "2024-01-01 / 2024-02-01"), 10))
        every { validateFieldsUseCase.validatePeriod("1") } returns ok
        every { validateFieldsUseCase.validateAdapter(adapter) } returns adapter
        every { validateFieldsUseCase.validateAdapterError(adapter) } returns ok

        val repoResult: ModelResult<List<ListPartialDomain?>, ModelError> = SuccessResult(emptyList())
        coEvery { registerListPartialUseCase.invoke(adapter) } returns repoResult

        val result = useCase("1", adapter)

        assertTrue(result.validationResult.isValid)
        assertNotNull(result.validationResult.operationResult)
        assertTrue(result.validationResult.operationResult is SuccessResult<*>)
        assertEquals(adapter, result.updatedListCalendar)
    }
}
