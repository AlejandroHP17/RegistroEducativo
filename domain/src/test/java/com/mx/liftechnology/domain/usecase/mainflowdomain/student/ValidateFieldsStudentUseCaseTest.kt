package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsStudentUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsStudentUseCaseTest {

    private lateinit var validateFieldsUseCase: ValidateFieldsStudentUseCase

    @Before
    fun setUp() {
        validateFieldsUseCase = ValidateFieldsStudentUseCaseImp()
    }

    //region Tests para validateName
    @Test
    fun `validateName con nombre valido`() {
        val result = validateFieldsUseCase.validateName("Juan")
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    @Test
    fun `validateName con nombre vacio`() {
        val result = validateFieldsUseCase.validateName("")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }
    //endregion

    //region Tests para validateCurp
    @Test
    fun `validateCurp con CURP valido`() {
        val result = validateFieldsUseCase.validateCurp("HEPA940117HDFRLL04")
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    @Test
    fun `validateCurp con CURP invalido`() {
        val result = validateFieldsUseCase.validateCurp("XAXX010101HXXIXXA")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_CURP_FORMAT_MISTAKE, result.errorMessage)
    }

    @Test
    fun `validateCurp con CURP vacio`() {
        val result = validateFieldsUseCase.validateCurp("")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }

    @Test
    fun `validateCurp con CURP nulo`() {
        val result = validateFieldsUseCase.validateCurp(null)
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }
    //endregion
}