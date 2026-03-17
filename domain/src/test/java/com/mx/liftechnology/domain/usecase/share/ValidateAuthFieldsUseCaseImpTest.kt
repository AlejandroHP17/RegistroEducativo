package com.mx.liftechnology.domain.usecase.share

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateAuthFieldsUseCaseImp].
 *
 * Se cubren los escenarios principales de validación de email, contraseña y códigos,
 * asegurando que los códigos de error y los flags de `isError` sean correctos.
 */
class ValidateAuthFieldsUseCaseImpTest {

    private lateinit var useCase: ValidateAuthFieldsUseCase

    @Before
    fun setup() {
        useCase = ValidateAuthFieldsUseCaseImp()
    }

    // ========== validateEmailCompose ==========

    @Test
    fun `validateEmailCompose retorna error ET_EMPTY cuando el email es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateEmailCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateEmailCompose("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateEmailCompose retorna error ET_USER_FORMAT_MISTAKE cuando el email es invalido`() {
        val result: ModelStateOutFieldText = useCase.validateEmailCompose("correo-invalido")

        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_USER_FORMAT_MISTAKE, result.errorMessage)
    }

    @Test
    fun `validateEmailCompose retorna formato correcto cuando el email es valido`() {
        val result: ModelStateOutFieldText = useCase.validateEmailCompose("user@test.com")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validatePassCompose ==========

    @Test
    fun `validatePassCompose retorna error ET_EMPTY cuando la contrasena es nula o vacia`() {
        val resultNull: ModelStateOutFieldText = useCase.validatePassCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validatePassCompose("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validatePassCompose retorna formato correcto cuando la contrasena tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validatePassCompose("abc")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validatePassRegisterCompose ==========

    @Test
    fun `validatePassRegisterCompose retorna error ET_EMPTY cuando la contrasena es nula o vacia`() {
        val resultNull: ModelStateOutFieldText = useCase.validatePassRegisterCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validatePassRegisterCompose("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validatePassRegisterCompose retorna error ET_PASS_FORMAT_MISTAKE cuando la contrasena no cumple regex`() {
        val result: ModelStateOutFieldText = useCase.validatePassRegisterCompose("abc123")

        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_PASS_FORMAT_MISTAKE, result.errorMessage)
    }

    @Test
    fun `validatePassRegisterCompose retorna formato correcto cuando la contrasena es valida`() {
        val result: ModelStateOutFieldText = useCase.validatePassRegisterCompose("Password123")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateRepeatPassCompose ==========

    @Test
    fun `validateRepeatPassCompose retorna error ET_EMPTY cuando repeatPass es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateRepeatPassCompose("Password123", null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateRepeatPassCompose("Password123", "")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateRepeatPassCompose retorna error ET_PASS_DIFFERENT_MISTAKE cuando las contrasenas no coinciden`() {
        val result: ModelStateOutFieldText = useCase.validateRepeatPassCompose("Password123", "Password456")

        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_PASS_DIFFERENT_MISTAKE, result.errorMessage)
    }

    @Test
    fun `validateRepeatPassCompose retorna formato correcto cuando las contrasenas coinciden`() {
        val result: ModelStateOutFieldText = useCase.validateRepeatPassCompose("Password123", "Password123")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateCodeCompose ==========

    @Test
    fun `validateCodeCompose retorna error ET_EMPTY cuando el codigo es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateCodeCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateCodeCompose("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateCodeCompose retorna formato correcto cuando el codigo tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateCodeCompose("ABC123")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }
}
