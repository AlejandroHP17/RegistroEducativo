package com.mx.liftechnology.domain.usecase.evaluation

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsEvaluationUseCaseImp].
 *
 * Se cubren los escenarios principales de validación de campos de evaluación:
 * - Nombre del trabajo (validación de no vacío)
 * - Nombre de la asignación (validación de no vacío)
 * - Fecha (validación de no vacío)
 */
class ValidateFieldsEvaluationUseCaseImpTest {

    private lateinit var useCase: ValidateFieldsEvaluationUseCase

    @Before
    fun setup() {
        useCase = ValidateFieldsEvaluationUseCaseImp()
    }

    // ========== validateNameJob ==========

    @Test
    fun `validateNameJob retorna error ET_EMPTY cuando el nombre es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateNameJob(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateNameJob("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateNameJob retorna formato correcto cuando el nombre tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateNameJob("Tarea 1")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateNameAssignment ==========

    @Test
    fun `validateNameAssignment retorna error ET_EMPTY cuando el nombre es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateNameAssignment(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateNameAssignment("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateNameAssignment retorna formato correcto cuando el nombre tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateNameAssignment("Asignación 1")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateDate ==========

    @Test
    fun `validateDate retorna error ET_EMPTY cuando la fecha es nula o vacia`() {
        val resultNull: ModelStateOutFieldText = useCase.validateDate(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateDate("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateDate retorna formato correcto cuando la fecha tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateDate("2024-01-15")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }
}
