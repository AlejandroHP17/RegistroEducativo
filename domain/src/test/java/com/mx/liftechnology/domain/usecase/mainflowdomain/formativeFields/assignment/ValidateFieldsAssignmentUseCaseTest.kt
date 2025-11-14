package com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.assignment

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsEvaluationUseCase
import com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsEvaluationUseCaseImp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsEvaluationUseCase].
 * Verifica el comportamiento de las funciones de validación del caso de uso.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsAssignmentUseCaseTest {

    private lateinit var useCase: ValidateFieldsEvaluationUseCase

    @Before
    fun setUp() {
        useCase = ValidateFieldsEvaluationUseCaseImp()
    }

    //region Tests para validateNameJob
    @Test
    fun `validateNameJob con nombre valido devuelve exito`() {
        assertFalse(useCase.validateNameJob("Examen Final").isError)
    }

    @Test
    fun `validateNameJob con nombre vacio devuelve error`() {
        val result = useCase.validateNameJob("")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }
    //endregion

    //region Tests para validateNameAssignment
    @Test
    fun `validateNameAssignment con nombre valido devuelve exito`() {
        assertFalse(useCase.validateNameAssignment("Unidad 1").isError)
    }

    @Test
    fun `validateNameAssignment con nombre vacio devuelve error`() {
        assertTrue(useCase.validateNameAssignment("").isError)
    }
    //endregion

    //region Tests para validateDate
    @Test
    fun `validateDate con fecha valida devuelve exito`() {
        assertFalse(useCase.validateDate("2024-12-25").isError)
    }

    @Test
    fun `validateDate con fecha vacia devuelve error`() {
        assertTrue(useCase.validateDate("").isError)
    }
    //endregion
}