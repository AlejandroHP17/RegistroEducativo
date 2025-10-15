package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsSubjectUseCase].
 * Verifica el comportamiento de las funciones de validación del caso de uso.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsSubjectUseCaseTest {

    private lateinit var useCase: ValidateFieldsSubjectUseCase

    @Before
    fun setUp() {
        useCase = ValidateFieldsSubjectUseCaseImp()
    }

    //region Tests para validateNameCompose
    @Test
    fun `validateNameCompose con nombre valido devuelve exito`() {
        assertFalse(useCase.validateNameCompose("Matemáticas").isError)
    }

    @Test
    fun `validateNameCompose con nombre vacio devuelve error`() {
        assertTrue(useCase.validateNameCompose("").isError)
    }
    //endregion

    //region Tests para validateOptionCompose
    @Test
    fun `validateOptionCompose con opcion valida devuelve exito`() {
        assertFalse(useCase.validateOptionCompose("3").isError)
    }

    @Test
    fun `validateOptionCompose con opcion vacia devuelve error`() {
        assertTrue(useCase.validateOptionCompose("").isError)
    }
    //endregion

    //region Tests para validateListJobsCompose
    @Test
    fun `validateListJobsCompose con campos validos no marca errores`() {
        val list = mutableListOf(ModelSpinnersWorkMethods(1, 1, 1, ModelStateOutFieldText("Examen"), ModelStateOutFieldText("50")))
        val result = useCase.validateListJobsCompose(list)
        assertFalse(result?.get(0)?.name?.isError ?: true)
        assertFalse(result?.get(0)?.percent?.isError ?: true)
    }

    @Test
    fun `validateListJobsCompose con campos vacios marca errores`() {
        val list = mutableListOf(ModelSpinnersWorkMethods(1, 1, 1, ModelStateOutFieldText(""), ModelStateOutFieldText("")))
        val result = useCase.validateListJobsCompose(list)
        assertTrue(result?.get(0)?.name?.isError ?: false)
        assertTrue(result?.get(0)?.percent?.isError ?: false)
    }
    //endregion

    //region Tests para validPercentCompose
    @Test
    fun `validPercentCompose con suma de 100 devuelve exito`() {
        val list = mutableListOf(
            ModelSpinnersWorkMethods(1, 1, 1, ModelStateOutFieldText("Examen"), ModelStateOutFieldText("50")),
            ModelSpinnersWorkMethods(2, 2, 2, ModelStateOutFieldText("Tareas"), ModelStateOutFieldText("50"))
        )
        assertFalse(useCase.validPercentCompose(list).isError)
    }

    @Test
    fun `validPercentCompose con suma diferente de 100 devuelve error`() {
        val list = mutableListOf(
            ModelSpinnersWorkMethods(1, 1, 1, ModelStateOutFieldText("Examen"), ModelStateOutFieldText("40"))
        )
        assertTrue(useCase.validPercentCompose(list).isError)
    }

    @Test
    fun `validPercentCompose con porcentaje cero devuelve error`() {
        val list = mutableListOf(
            ModelSpinnersWorkMethods(1, 1, 1, ModelStateOutFieldText("Examen"), ModelStateOutFieldText("0")),
             ModelSpinnersWorkMethods(2, 2, 2, ModelStateOutFieldText("Tareas"), ModelStateOutFieldText("100"))
        )
        assertTrue(useCase.validPercentCompose(list).isError)
    }
    //endregion
}