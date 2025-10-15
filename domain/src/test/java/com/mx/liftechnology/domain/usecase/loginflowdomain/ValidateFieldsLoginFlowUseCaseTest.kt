package com.mx.liftechnology.domain.usecase.loginflowdomain

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsLoginFlowUseCase].
 * Verifica todos los casos de validación para el flujo de login y registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsLoginFlowUseCaseTest {

    private lateinit var useCase: ValidateFieldsLoginFlowUseCase

    @Before
    fun setUp() {
        useCase = ValidateFieldsLoginFlowUseCaseImp()
    }

    //region Tests para validateEmailCompose
    @Test
    fun `validateEmailCompose con email valido devuelve formato correcto`() {
        val result = useCase.validateEmailCompose("test@example.mx")
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    @Test
    fun `validateEmailCompose con email invalido devuelve error de formato`() {
        val result = useCase.validateEmailCompose("test.com")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_USER_FORMAT_MISTAKE, result.errorMessage)
    }

    @Test
    fun `validateEmailCompose con email vacio devuelve error de campo vacio`() {
        val result = useCase.validateEmailCompose("")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }

    @Test
    fun `validateEmailCompose con email nulo devuelve error de campo vacio`() {
        val result = useCase.validateEmailCompose(null)
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }
    //endregion

    //region Tests para validatePassRegisterCompose
    @Test
    fun `validatePassRegisterCompose con password valido devuelve formato correcto`() {
        val result = useCase.validatePassRegisterCompose("Password123")
        assertFalse(result.isError)
    }

    @Test
    fun `validatePassRegisterCompose con password invalido devuelve error de formato`() {
        val result = useCase.validatePassRegisterCompose("pass")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_PASS_FORMAT_MISTAKE, result.errorMessage)
    }

    @Test
    fun `validatePassRegisterCompose con password vacio devuelve error de campo vacio`() {
        val result = useCase.validatePassRegisterCompose("")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }
    //endregion

    //region Tests para validateRepeatPassCompose
    @Test
    fun `validateRepeatPassCompose con passwords que coinciden devuelve formato correcto`() {
        val result = useCase.validateRepeatPassCompose("Password123", "Password123")
        assertFalse(result.isError)
    }

    @Test
    fun `validateRepeatPassCompose con passwords que no coinciden devuelve error de contraseñas diferentes`() {
        val result = useCase.validateRepeatPassCompose("Password123", "Password456")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_PASS_DIFFERENT_MISTAKE, result.errorMessage)
    }
    //endregion

    //region Tests para validateCodeCompose
    @Test
    fun `validateCodeCompose con codigo no vacio devuelve formato correcto`() {
        val result = useCase.validateCodeCompose("12345")
        assertFalse(result.isError)
    }

    @Test
    fun `validateCodeCompose con codigo vacio devuelve error de campo vacio`() {
        val result = useCase.validateCodeCompose("")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }
    //endregion
}