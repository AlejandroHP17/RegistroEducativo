package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsStudentUseCase].
 * Verifica todos los casos de validación para el formulario de registro de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsStudentUseCaseTest {

    private lateinit var useCase: ValidateFieldsStudentUseCase

    @Before
    fun setUp() {
        useCase = ValidateFieldsStudentUseCaseImp()
    }

    //region Tests para validateName
    @Test
    fun `validateName con nombre valido devuelve exito`() {
        assertFalse(useCase.validateName("Juan").isError)
    }

    @Test
    fun `validateName con nombre vacio devuelve error`() {
        assertTrue(useCase.validateName("").isError)
    }
    //endregion

    //region Tests para validateLastName
    @Test
    fun `validateLastName con apellido valido devuelve exito`() {
        assertFalse(useCase.validateLastName("Perez").isError)
    }

    @Test
    fun `validateLastName con apellido vacio devuelve error`() {
        assertTrue(useCase.validateLastName("").isError)
    }
    //endregion

    //region Tests para validateSecondLastName
    @Test
    fun `validateSecondLastName con apellido valido devuelve exito`() {
        assertFalse(useCase.validateSecondLastName("Gomez").isError)
    }

    @Test
    fun `validateSecondLastName con apellido vacio devuelve error`() {
        assertTrue(useCase.validateSecondLastName("").isError)
    }
    //endregion

    //region Tests para validateCurp
    @Test
    fun `validateCurp con CURP valido devuelve exito`() {
        assertFalse(useCase.validateCurp("XAXX010101HXXIXXA0").isError)
    }

    @Test
    fun `validateCurp con CURP invalido devuelve error`() {
        assertTrue(useCase.validateCurp("CURP_INVALIDA").isError)
    }

    @Test
    fun `validateCurp con CURP vacio devuelve error`() {
        assertTrue(useCase.validateCurp("").isError)
    }
    //endregion

    //region Tests para validateBirthday
    @Test
    fun `validateBirthday con fecha valida devuelve exito`() {
        assertFalse(useCase.validateBirthday("2001-01-01").isError)
    }

    @Test
    fun `validateBirthday con fecha vacia devuelve error`() {
        assertTrue(useCase.validateBirthday("").isError)
    }
    //endregion

    //region Tests para validatePhoneNumber
    @Test
    fun `validatePhoneNumber con numero valido devuelve exito`() {
        assertFalse(useCase.validatePhoneNumber("1234567890").isError)
    }

    @Test
    fun `validatePhoneNumber con numero invalido devuelve error`() {
        assertTrue(useCase.validatePhoneNumber("123").isError)
    }

    @Test
    fun `validatePhoneNumber con numero vacio devuelve error`() {
        assertTrue(useCase.validatePhoneNumber("").isError)
    }
    //endregion
}