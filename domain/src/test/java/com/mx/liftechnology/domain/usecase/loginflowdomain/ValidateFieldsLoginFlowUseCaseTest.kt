package com.mx.liftechnology.domain.usecase.loginflowdomain

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsLoginFlowUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsLoginFlowUseCaseTest {

    private lateinit var validateFieldsUseCase: ValidateFieldsLoginFlowUseCase

    @Before
    fun setUp() {
        validateFieldsUseCase = ValidateFieldsLoginFlowUseCaseImp()
    }

    //region Tests para validateEmailCompose
    @Test
    fun `validateEmailCompose con email valido`() {
        val result = validateFieldsUseCase.validateEmailCompose("test@example.com")
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    @Test
    fun `validateEmailCompose con email vacio`() {
        val result = validateFieldsUseCase.validateEmailCompose("")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }

    @Test
    fun `validateEmailCompose con email nulo`() {
        val result = validateFieldsUseCase.validateEmailCompose(null)
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }

    @Test
    fun `validateEmailCompose con formato invalido`() {
        val result = validateFieldsUseCase.validateEmailCompose("test.com")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_USER_FORMAT_MISTAKE, result.errorMessage)
    }
    //endregion

    //region Tests para validatePassRegisterCompose
    @Test
    fun `validatePassRegisterCompose con password valido`() {
        val result = validateFieldsUseCase.validatePassRegisterCompose("Password123")
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    @Test
    fun `validatePassRegisterCompose con password vacio`() {
        val result = validateFieldsUseCase.validatePassRegisterCompose("")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }

    @Test
    fun `validatePassRegisterCompose con formato invalido`() {
        val result = validateFieldsUseCase.validatePassRegisterCompose("pass")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_PASS_FORMAT_MISTAKE, result.errorMessage)
    }
    //endregion

    //region Tests para validateRepeatPassCompose
    @Test
    fun `validateRepeatPassCompose con passwords que coinciden`() {
        val result = validateFieldsUseCase.validateRepeatPassCompose("Password123", "Password123")
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    @Test
    fun `validateRepeatPassCompose con passwords que no coinciden`() {
        val result = validateFieldsUseCase.validateRepeatPassCompose("Password123", "Password456")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_PASS_DIFFERENT_MISTAKE, result.errorMessage)
    }
    //endregion
}