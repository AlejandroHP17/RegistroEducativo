package com.mx.liftechnology.domain.usecase.share

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsStudentUseCaseImp].
 *
 * Se cubren los escenarios principales de validación de campos de estudiante:
 * - Nombre, apellidos (validación de no vacío)
 * - CURP (validación de formato regex)
 * - Teléfono (validación de formato regex)
 * - Fecha de nacimiento (validación de no vacío)
 */
class ValidateFieldsStudentUseCaseImpTest {

    private lateinit var useCase: ValidateFieldsStudentUseCase

    @Before
    fun setup() {
        useCase = ValidateFieldsStudentUseCaseImp()
    }

    // ========== validateName ==========

    @Test
    fun `validateName retorna error ET_EMPTY cuando el nombre es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateName(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateName("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateName retorna formato correcto cuando el nombre tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateName("Juan")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateLastName ==========

    @Test
    fun `validateLastName retorna error ET_EMPTY cuando el apellido es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateLastName(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateLastName("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateLastName retorna formato correcto cuando el apellido tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateLastName("Pérez")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateSecondLastName ==========

    @Test
    fun `validateSecondLastName retorna error ET_EMPTY cuando el apellido materno es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateSecondLastName(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateSecondLastName("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateSecondLastName retorna formato correcto cuando el apellido materno tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateSecondLastName("López")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateCurp ==========

    @Test
    fun `validateCurp retorna error ET_EMPTY cuando la CURP es nula o vacia`() {
        val resultNull: ModelStateOutFieldText = useCase.validateCurp(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateCurp("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateCurp retorna error ET_CURP_FORMAT_MISTAKE cuando la CURP no cumple el formato`() {
        val result: ModelStateOutFieldText = useCase.validateCurp("INVALID_CURP")

        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_CURP_FORMAT_MISTAKE, result.errorMessage)
    }

    @Test
    fun `validateCurp retorna formato correcto cuando la CURP es valida`() {
        val result: ModelStateOutFieldText = useCase.validateCurp("PELJ950101HDFRRN01")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validatePhoneNumber ==========

    @Test
    fun `validatePhoneNumber retorna error ET_EMPTY cuando el telefono es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validatePhoneNumber(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validatePhoneNumber("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validatePhoneNumber retorna error ET_PHONE_NUMBER_FORMAT_MISTAKE cuando el telefono no tiene 10 digitos`() {
        val resultShort: ModelStateOutFieldText = useCase.validatePhoneNumber("12345")
        val resultLong: ModelStateOutFieldText = useCase.validatePhoneNumber("12345678901")
        val resultWithLetters: ModelStateOutFieldText = useCase.validatePhoneNumber("123456789a")

        assertTrue(resultShort.isError)
        assertEquals(ModelCodeInputs.ET_PHONE_NUMBER_FORMAT_MISTAKE, resultShort.errorMessage)

        assertTrue(resultLong.isError)
        assertEquals(ModelCodeInputs.ET_PHONE_NUMBER_FORMAT_MISTAKE, resultLong.errorMessage)

        assertTrue(resultWithLetters.isError)
        assertEquals(ModelCodeInputs.ET_PHONE_NUMBER_FORMAT_MISTAKE, resultWithLetters.errorMessage)
    }

    @Test
    fun `validatePhoneNumber retorna formato correcto cuando el telefono tiene 10 digitos`() {
        val result: ModelStateOutFieldText = useCase.validatePhoneNumber("5512345678")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateBirthday ==========

    @Test
    fun `validateBirthday retorna error ET_EMPTY cuando la fecha es nula o vacia`() {
        val resultNull: ModelStateOutFieldText = useCase.validateBirthday(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateBirthday("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateBirthday retorna formato correcto cuando la fecha tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateBirthday("1995-01-01")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }
}
