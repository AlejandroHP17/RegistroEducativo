package com.mx.liftechnology.domain.usecase.school

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.model.schoolCycle.RegisterSchoolCycleDomain
import com.mx.liftechnology.domain.usecase.schoolCycle.RegisterCycleSchoolUseCase
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
 * Tests para [RegisterSchoolWithValidationUseCase].
 */
class RegisterSchoolWithValidationUseCaseTest {

    private lateinit var validateFieldsUseCase: ValidateFieldsRegisterSchoolUseCase
    private lateinit var registerCycleSchoolUseCase: RegisterCycleSchoolUseCase
    private lateinit var useCase: RegisterSchoolWithValidationUseCase

    @Before
    fun setup() {
        validateFieldsUseCase = mockk()
        registerCycleSchoolUseCase = mockk()
        useCase = RegisterSchoolWithValidationUseCase(validateFieldsUseCase, registerCycleSchoolUseCase)
    }

    @Test
    fun `cuando hay errores de validacion retorna resultado invalido y no registra`() = runTest {
        val error = ModelStateOutFieldText(isError = true, errorMessage = ModelCodeInputs.ET_EMPTY)
        val ok = ModelStateOutFieldText(isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)

        every { validateFieldsUseCase.validateCctCompose("CCT123") } returns ok
        every { validateFieldsUseCase.validateTypeCompose("1") } returns ok
        every { validateFieldsUseCase.validateGradeCompose("1") } returns ok
        every { validateFieldsUseCase.validateGroupCompose("A") } returns ok
        every { validateFieldsUseCase.validateCycleCompose("1") } returns ok
        every { validateFieldsUseCase.validateLabelCycleCompose(null) } returns error

        val result: ModelValidationResult<RegisterSchoolCycleDomain> = useCase(
            cct = "CCT123",
            type = "1",
            grade = "1",
            group = "A",
            cycle = "1",
            labelCycle = null,
            schoolId = 1,
            periodCatalogId = 1,
            shiftName = "Matutino"
        )

        assertFalse(result.isValid)
        assertTrue(result.operationResult == null)
        coVerify(exactly = 0) { registerCycleSchoolUseCase.invoke(any(), any(), any(), any(), any(), any(), any(), any()) }
    }

    @Test
    fun `cuando validaciones son correctas registra y retorna resultado valido`() = runTest {
        val ok = ModelStateOutFieldText(isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)

        every { validateFieldsUseCase.validateCctCompose("CCT123") } returns ok
        every { validateFieldsUseCase.validateTypeCompose("1") } returns ok
        every { validateFieldsUseCase.validateGradeCompose("1") } returns ok
        every { validateFieldsUseCase.validateGroupCompose("A") } returns ok
        every { validateFieldsUseCase.validateCycleCompose("1") } returns ok
        every { validateFieldsUseCase.validateLabelCycleCompose("2024-2025") } returns ok

        val domain = RegisterSchoolCycleDomain(cycleSchoolId = 1, name = "", cct = "", grade = "", group = "", periodCatalogId = 1, shiftName = "")
        val repoResult: ModelResult<RegisterSchoolCycleDomain, ModelError> = SuccessResult(domain)
        coEvery {
            registerCycleSchoolUseCase.invoke(
                schoolId = 1,
                periodCatalogId = 1,
                cct = "CCT123",
                grade = 1,
                group = "A",
                cycle = 1,
                shiftName = "Matutino",
                labelCycleState = "2024-2025"
            )
        } returns repoResult

        val result: ModelValidationResult<RegisterSchoolCycleDomain> = useCase(
            cct = "CCT123",
            type = "1",
            grade = "1",
            group = "A",
            cycle = "1",
            labelCycle = "2024-2025",
            schoolId = 1,
            periodCatalogId = 1,
            shiftName = "Matutino"
        )

        assertTrue(result.isValid)
        assertNotNull(result.operationResult)
        assertTrue(result.operationResult is SuccessResult<*>)
    }
}
