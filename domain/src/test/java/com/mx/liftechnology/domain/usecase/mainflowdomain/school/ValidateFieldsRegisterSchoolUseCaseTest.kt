package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import com.mx.liftechnology.domain.usecase.schoolCycle.school.ValidateFieldsRegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.ValidateFieldsRegisterSchoolUseCaseImp
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsRegisterSchoolUseCase].
 * Verifica el comportamiento de las funciones de validación del caso de uso.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsRegisterSchoolUseCaseTest {

    private lateinit var useCase: ValidateFieldsRegisterSchoolUseCase

    @Before
    fun setUp() {
        useCase = ValidateFieldsRegisterSchoolUseCaseImp()
    }

    //region Tests para validateGradeCompose
    @Test
    fun `validateGradeCompose con grado valido devuelve exito`() {
        val result = useCase.validateGradeCompose("5")
        assertFalse(result.isError)
    }

    @Test
    fun `validateGradeCompose con grado invalido (texto) devuelve error`() {
        val result = useCase.validateGradeCompose("abc")
        assertTrue(result.isError)
    }

    @Test
    fun `validateGradeCompose con grado vacio devuelve error`() {
        val result = useCase.validateGradeCompose("")
        assertTrue(result.isError)
    }
    //endregion

    //region Tests para validateGroupCompose
    @Test
    fun `validateGroupCompose con grupo valido devuelve exito`() {
        val result = useCase.validateGroupCompose("A")
        assertFalse(result.isError)
    }

    @Test
    fun `validateGroupCompose con grupo vacio devuelve error`() {
        val result = useCase.validateGroupCompose("")
        assertTrue(result.isError)
    }
    //endregion

    //region Tests para validateCycleCompose
    @Test
    fun `validateCycleCompose con ciclo valido devuelve exito`() {
        val result = useCase.validateCycleCompose("3")
        assertFalse(result.isError)
    }

    @Test
    fun `validateCycleCompose con ciclo invalido (texto) devuelve error`() {
        val result = useCase.validateCycleCompose("abc")
        assertTrue(result.isError)
    }
    //endregion

    //region Tests para validateCctCompose
    @Test
    fun `validateCctCompose con CCT valido devuelve exito`() {
        val result = useCase.validateCctCompose("1234567890")
        assertFalse(result.isError)
    }

    @Test
    fun `validateCctCompose con CCT invalido (longitud) devuelve error`() {
        val result = useCase.validateCctCompose("123")
        assertTrue(result.isError)
    }
    //endregion
}