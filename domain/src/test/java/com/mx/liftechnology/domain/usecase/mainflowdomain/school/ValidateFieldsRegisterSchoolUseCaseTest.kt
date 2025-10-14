package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsRegisterSchoolUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsRegisterSchoolUseCaseTest {

    private lateinit var validateFieldsUseCase: ValidateFieldsRegisterSchoolUseCase

    @Before
    fun setUp() {
        validateFieldsUseCase = ValidateFieldsRegisterSchoolUseCaseImp()
    }

    //region Tests para validateCctCompose
    @Test
    fun `validateCctCompose con CCT valido`() {
        val result = validateFieldsUseCase.validateCctCompose("1234567890")
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    @Test
    fun `validateCctCompose con CCT invalido`() {
        val result = validateFieldsUseCase.validateCctCompose("12345")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_NOT_FOUND, result.errorMessage)
    }

    @Test
    fun `validateCctCompose con CCT vacio`() {
        val result = validateFieldsUseCase.validateCctCompose("")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }

    @Test
    fun `validateCctCompose con CCT nulo`() {
        val result = validateFieldsUseCase.validateCctCompose(null)
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }
    //endregion
}