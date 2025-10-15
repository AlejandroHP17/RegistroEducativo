package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsRegisterPartialUseCase].
 * Verifica el comportamiento de las funciones de validación del caso de uso.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsRegisterPartialUseCaseTest {

    private lateinit var useCase: ValidateFieldsRegisterPartialUseCase

    @Before
    fun setUp() {
        useCase = ValidateFieldsRegisterPartialUseCaseImp()
    }

    //region Tests para validatePeriod
    @Test
    fun `validatePeriod con periodo valido devuelve exito`() {
        val result = useCase.validatePeriod("3")
        assertFalse(result.isError)
    }

    @Test
    fun `validatePeriod con periodo invalido (cero) devuelve error`() {
        val result = useCase.validatePeriod("0")
        assertTrue(result.isError)
    }

    @Test
    fun `validatePeriod con periodo nulo devuelve error`() {
        val result = useCase.validatePeriod(null)
        assertTrue(result.isError)
    }
    //endregion

    //region Tests para validateAdapter
    @Test
    fun `validateAdapter con fechas validas no marca errores`() {
        val periods = listOf(
            ModelDatePeriodDomain(1, ModelStateOutFieldText("2024-01-01 / 2024-02-01"), 1)
        )
        val result = useCase.validateAdapter(periods)
        assertFalse(result?.get(0)?.date?.isError ?: true)
    }

    @Test
    fun `validateAdapter con fecha vacia marca error`() {
        val periods = listOf(
            ModelDatePeriodDomain(1, ModelStateOutFieldText(""), 1)
        )
        val result = useCase.validateAdapter(periods)
        assertTrue(result?.get(0)?.date?.isError ?: false)
    }
    //endregion

    //region Tests para validateAdapterError
    @Test
    fun `validateAdapterError con lista sin errores devuelve exito`() {
        val periods = listOf(
            ModelDatePeriodDomain(1, ModelStateOutFieldText("2024-01-01"), 1)
        )
        val result = useCase.validateAdapterError(periods)
        assertFalse(result.isError)
    }

    @Test
    fun `validateAdapterError con lista con errores devuelve error`() {
        val periods = listOf(
            ModelDatePeriodDomain(1, ModelStateOutFieldText("", isError = true), 1)
        )
        val result = useCase.validateAdapterError(periods)
        assertTrue(result.isError)
    }
    //endregion
}