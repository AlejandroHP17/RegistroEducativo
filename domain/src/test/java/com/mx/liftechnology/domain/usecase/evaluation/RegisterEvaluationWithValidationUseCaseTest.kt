package com.mx.liftechnology.domain.usecase.evaluation

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.evaluation.CardDomain
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
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
 * Tests para [RegisterEvaluationWithValidationUseCase].
 */
class RegisterEvaluationWithValidationUseCaseTest {

    private lateinit var validateFieldsUseCase: ValidateFieldsEvaluationUseCase
    private lateinit var registerWorkTypeEvaluationsUseCase: RegisterWorkTypeEvaluationsUseCase
    private lateinit var useCase: RegisterEvaluationWithValidationUseCase

    @Before
    fun setup() {
        validateFieldsUseCase = mockk()
        registerWorkTypeEvaluationsUseCase = mockk()
        useCase = RegisterEvaluationWithValidationUseCase(validateFieldsUseCase, registerWorkTypeEvaluationsUseCase)
    }

    @Test
    fun `cuando hay errores de validacion no se ejecuta el registro y el resultado es invalido`() = runTest {
        val errorState = ModelStateOutFieldText(valueText = "", isError = true, errorMessage = ModelCodeInputs.ET_EMPTY)
        val okState = ModelStateOutFieldText(valueText = "ok", isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)

        every { validateFieldsUseCase.validateNameJob(null) } returns errorState
        every { validateFieldsUseCase.validateNameAssignment("Asignacion") } returns okState
        every { validateFieldsUseCase.validateDate("2024-01-10") } returns okState

        val result: ModelValidationResult<Boolean> = useCase(
            nameJob = null,
            nameAssignment = "Asignacion",
            date = "2024-01-10",
            workTypeId = 1,
            studentListUI = emptyList()
        )

        assertFalse(result.isValid)
        assertNull(result.operationResult)
        assertTrue(result.validationStates["nameJob"]!!.isError)
        assertFalse(result.validationStates["nameAssignment"]!!.isError)
        assertFalse(result.validationStates["date"]!!.isError)

        coVerify(exactly = 0) { registerWorkTypeEvaluationsUseCase.invoke(any(), any(), any(), any()) }
    }

    @Test
    fun `cuando validaciones son correctas se ejecuta el registro y el resultado es valido`() = runTest {
        val okState = ModelStateOutFieldText(valueText = "ok", isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)

        every { validateFieldsUseCase.validateNameJob("Tarea 1") } returns okState
        every { validateFieldsUseCase.validateNameAssignment("Asignacion") } returns okState
        every { validateFieldsUseCase.validateDate("2024-01-10") } returns okState

        val students = listOf(CardDomain(studentId = 1, grade = 9.0))
        val opResult: ModelResult<Boolean, ModelError> = SuccessResult(true)

        coEvery { registerWorkTypeEvaluationsUseCase.invoke(1, "Tarea 1", "2024-01-10", students) } returns opResult

        val result: ModelValidationResult<Boolean> = useCase(
            nameJob = "Tarea 1",
            nameAssignment = "Asignacion",
            date = "2024-01-10",
            workTypeId = 1,
            studentListUI = students
        )

        assertTrue(result.isValid)
        assertNotNull(result.operationResult)
        assertTrue(result.operationResult is SuccessResult<*>)
        assertEquals(true, (result.operationResult as SuccessResult).data)

        coVerify(exactly = 1) { registerWorkTypeEvaluationsUseCase.invoke(1, "Tarea 1", "2024-01-10", students) }
    }

    @Test
    fun `cuando registro devuelve ErrorResult el resultado valido contiene el error`() = runTest {
        val okState = ModelStateOutFieldText(valueText = "ok", isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)

        every { validateFieldsUseCase.validateNameJob("Tarea 1") } returns okState
        every { validateFieldsUseCase.validateNameAssignment("Asignacion") } returns okState
        every { validateFieldsUseCase.validateDate("2024-01-10") } returns okState

        val students = emptyList<CardDomain>()
        val errorResult: ModelResult<Boolean, ModelError> = ErrorResult(mockk())

        coEvery { registerWorkTypeEvaluationsUseCase.invoke(1, "Tarea 1", "2024-01-10", students) } returns errorResult

        val result: ModelValidationResult<Boolean> = useCase(
            nameJob = "Tarea 1",
            nameAssignment = "Asignacion",
            date = "2024-01-10",
            workTypeId = 1,
            studentListUI = students
        )

        assertTrue(result.isValid)
        assertNotNull(result.operationResult)
        assertTrue(result.operationResult is ErrorResult<*>)

        coVerify(exactly = 1) { registerWorkTypeEvaluationsUseCase.invoke(1, "Tarea 1", "2024-01-10", students) }
    }
}
