package com.mx.liftechnology.domain.usecase.school

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsRegisterSchoolUseCaseImp].
 *
 * Se cubren los escenarios principales de validación de campos de registro de escuela:
 * - Tipo (validación de no vacío)
 * - Grado (validación de no vacío y formato numérico)
 * - Grupo (validación de no vacío)
 * - Ciclo (validación de no vacío y formato numérico)
 * - Etiqueta de ciclo (validación de no vacío)
 * - CCT (validación de no vacío y longitud de 10 caracteres)
 */
class ValidateFieldsRegisterSchoolUseCaseImpTest {

    private lateinit var useCase: ValidateFieldsRegisterSchoolUseCase

    @Before
    fun setup() {
        useCase = ValidateFieldsRegisterSchoolUseCaseImp()
    }

    // ========== validateTypeCompose ==========

    @Test
    fun `validateTypeCompose retorna error ET_SPINNER_EMPTY cuando el tipo es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateTypeCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateTypeCompose("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_SPINNER_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_SPINNER_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateTypeCompose retorna formato correcto cuando el tipo tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateTypeCompose("Primaria")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateGradeCompose ==========

    @Test
    fun `validateGradeCompose retorna error ET_SPINNER_EMPTY cuando el grado es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateGradeCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateGradeCompose("")
        val resultBlank: ModelStateOutFieldText = useCase.validateGradeCompose("   ")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_SPINNER_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_SPINNER_EMPTY, resultEmpty.errorMessage)

        assertTrue(resultBlank.isError)
        assertEquals(ModelCodeInputs.ET_SPINNER_EMPTY, resultBlank.errorMessage)
    }

    @Test
    fun `validateGradeCompose retorna error ET_MISTAKE_FORMAT cuando el grado no es numerico`() {
        val result: ModelStateOutFieldText = useCase.validateGradeCompose("abc")

        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_MISTAKE_FORMAT, result.errorMessage)
    }

    @Test
    fun `validateGradeCompose retorna formato correcto cuando el grado es numerico`() {
        val result: ModelStateOutFieldText = useCase.validateGradeCompose("5")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateGroupCompose ==========

    @Test
    fun `validateGroupCompose retorna error ET_SPINNER_EMPTY cuando el grupo es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateGroupCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateGroupCompose("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_SPINNER_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_SPINNER_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateGroupCompose retorna formato correcto cuando el grupo tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateGroupCompose("A")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateCycleCompose ==========

    @Test
    fun `validateCycleCompose retorna error ET_SPINNER_EMPTY cuando el ciclo es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateCycleCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateCycleCompose("")
        val resultBlank: ModelStateOutFieldText = useCase.validateCycleCompose("   ")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_SPINNER_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_SPINNER_EMPTY, resultEmpty.errorMessage)

        assertTrue(resultBlank.isError)
        assertEquals(ModelCodeInputs.ET_SPINNER_EMPTY, resultBlank.errorMessage)
    }

    @Test
    fun `validateCycleCompose retorna error ET_MISTAKE_FORMAT cuando el ciclo no es numerico`() {
        val result: ModelStateOutFieldText = useCase.validateCycleCompose("abc")

        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_MISTAKE_FORMAT, result.errorMessage)
    }

    @Test
    fun `validateCycleCompose retorna formato correcto cuando el ciclo es numerico`() {
        val result: ModelStateOutFieldText = useCase.validateCycleCompose("2024")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateLabelCycleCompose ==========

    @Test
    fun `validateLabelCycleCompose retorna error ET_EMPTY cuando la etiqueta es nula o vacia`() {
        val resultNull: ModelStateOutFieldText = useCase.validateLabelCycleCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateLabelCycleCompose("")
        val resultBlank: ModelStateOutFieldText = useCase.validateLabelCycleCompose("   ")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)

        assertTrue(resultBlank.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultBlank.errorMessage)
    }

    @Test
    fun `validateLabelCycleCompose retorna formato correcto cuando la etiqueta tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateLabelCycleCompose("2024-2025")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateCctCompose ==========

    @Test
    fun `validateCctCompose retorna error ET_EMPTY cuando la CCT es nula o vacia`() {
        val resultNull: ModelStateOutFieldText = useCase.validateCctCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateCctCompose("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateCctCompose retorna error ET_NOT_FOUND cuando la CCT no tiene 10 caracteres`() {
        val resultShort: ModelStateOutFieldText = useCase.validateCctCompose("12345")
        val resultLong: ModelStateOutFieldText = useCase.validateCctCompose("12345678901")

        assertTrue(resultShort.isError)
        assertEquals(ModelCodeInputs.ET_NOT_FOUND, resultShort.errorMessage)

        assertTrue(resultLong.isError)
        assertEquals(ModelCodeInputs.ET_NOT_FOUND, resultLong.errorMessage)
    }

    @Test
    fun `validateCctCompose retorna formato correcto cuando la CCT tiene 10 caracteres`() {
        val result: ModelStateOutFieldText = useCase.validateCctCompose("1234567890")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }
}
