package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsSubjectUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsSubjectUseCaseTest {

    private lateinit var validateFieldsUseCase: ValidateFieldsSubjectUseCase

    @Before
    fun setUp() {
        validateFieldsUseCase = ValidateFieldsSubjectUseCaseImp()
    }

    //region Tests para validateNameCompose
    @Test
    fun `validateNameCompose con nombre de materia valido`() {
        val result = validateFieldsUseCase.validateNameCompose("Matemáticas")
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    @Test
    fun `validateNameCompose con nombre de materia vacio`() {
        val result = validateFieldsUseCase.validateNameCompose("")
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, result.errorMessage)
    }
    //endregion

    //region Tests para validPercentCompose
    @Test
    fun `validPercentCompose con porcentajes que suman 100`() {
        val listJobs = mutableListOf(
            ModelSpinnersWorkMethods(1, 1, 1, "Examen".stringToModelStateOutFieldText(), "50".stringToModelStateOutFieldText()),
            ModelSpinnersWorkMethods(2, 2, 2, "Tareas".stringToModelStateOutFieldText(), "50".stringToModelStateOutFieldText())
        )
        val result = validateFieldsUseCase.validPercentCompose(listJobs)
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    @Test
    fun `validPercentCompose con porcentajes que no suman 100`() {
        val listJobs = mutableListOf(
            ModelSpinnersWorkMethods(1, 1, 1, "Examen".stringToModelStateOutFieldText(), "40".stringToModelStateOutFieldText()),
            ModelSpinnersWorkMethods(2, 2, 2, "Tareas".stringToModelStateOutFieldText(), "50".stringToModelStateOutFieldText())
        )
        val result = validateFieldsUseCase.validPercentCompose(listJobs)
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.SP_NOT, result.errorMessage)
    }
    //endregion
}