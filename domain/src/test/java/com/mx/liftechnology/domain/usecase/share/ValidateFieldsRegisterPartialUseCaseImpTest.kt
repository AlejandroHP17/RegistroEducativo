package com.mx.liftechnology.domain.usecase.share

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsRegisterPartialUseCaseImp].
 *
 * Se cubren los escenarios principales de validación de campos de parciales:
 * - Validación de período (número >= 1)
 * - Validación de adaptador (lista de períodos con fechas)
 * - Validación de errores en el adaptador
 */
class ValidateFieldsRegisterPartialUseCaseImpTest {

    private lateinit var useCase: ValidateFieldsRegisterPartialUseCase

    @Before
    fun setup() {
        useCase = ValidateFieldsRegisterPartialUseCaseImp()
    }

    // ========== validatePeriod ==========

    @Test
    fun `validatePeriod retorna error ET_EMPTY cuando el periodo es nulo o menor a 1`() {
        val resultNull: ModelStateOutFieldText = useCase.validatePeriod(null)
        val resultZero: ModelStateOutFieldText = useCase.validatePeriod("0")
        val resultNegative: ModelStateOutFieldText = useCase.validatePeriod("-1")
        val resultInvalid: ModelStateOutFieldText = useCase.validatePeriod("abc")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultZero.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultZero.errorMessage)

        assertTrue(resultNegative.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNegative.errorMessage)

        assertTrue(resultInvalid.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultInvalid.errorMessage)
    }

    @Test
    fun `validatePeriod retorna formato correcto cuando el periodo es mayor o igual a 1`() {
        val resultOne: ModelStateOutFieldText = useCase.validatePeriod("1")
        val resultGreater: ModelStateOutFieldText = useCase.validatePeriod("5")

        assertFalse(resultOne.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, resultOne.errorMessage)

        assertFalse(resultGreater.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, resultGreater.errorMessage)
    }

    // ========== validateAdapter ==========

    @Test
    fun `validateAdapter retorna null cuando la lista es null`() {
        val result: List<DatePeriodDomain>? = useCase.validateAdapter(null)

        assertNull(result)
    }

    @Test
    fun `validateAdapter marca como error los periodos sin fecha`() {
        // Given
        val periods = listOf(
            DatePeriodDomain(
                position = 1,
                date = ModelStateOutFieldText(valueText = "2024-01-01", isError = false, errorMessage = ""),
                partialCycleGroup = null
            ),
            DatePeriodDomain(
                position = 2,
                date = ModelStateOutFieldText(valueText = "", isError = false, errorMessage = ""),
                partialCycleGroup = null
            ),
            DatePeriodDomain(
                position = 3,
                date = ModelStateOutFieldText(valueText = null, isError = false, errorMessage = ""),
                partialCycleGroup = null
            )
        )

        // When
        val result: List<DatePeriodDomain>? = useCase.validateAdapter(periods)

        // Then
        assertNotNull(result)
        assertEquals(3, result!!.size)
        assertFalse(result[0].date.isError) // Tiene fecha válida
        assertTrue(result[1].date.isError) // Fecha vacía
        assertTrue(result[2].date.isError) // Fecha nula
        assertEquals(ModelCodeInputs.SP_NOT_OPTION, result[1].date.errorMessage)
        assertEquals(ModelCodeInputs.SP_NOT_OPTION, result[2].date.errorMessage)
    }

    @Test
    fun `validateAdapter no marca error cuando todos los periodos tienen fecha`() {
        // Given
        val periods = listOf(
            DatePeriodDomain(
                position = 1,
                date = ModelStateOutFieldText(valueText = "2024-01-01", isError = false, errorMessage = ""),
                partialCycleGroup = null
            ),
            DatePeriodDomain(
                position = 2,
                date = ModelStateOutFieldText(valueText = "2024-02-01", isError = false, errorMessage = ""),
                partialCycleGroup = null
            )
        )

        // When
        val result: List<DatePeriodDomain>? = useCase.validateAdapter(periods)

        // Then
        assertNotNull(result)
        assertEquals(2, result!!.size)
        assertFalse(result[0].date.isError)
        assertFalse(result[1].date.isError)
    }

    // ========== validateAdapterError ==========

    @Test
    fun `validateAdapterError retorna error cuando hay periodos con error`() {
        // Given
        val periods = listOf(
            DatePeriodDomain(
                position = 1,
                date = ModelStateOutFieldText(valueText = "2024-01-01", isError = false, errorMessage = ""),
                partialCycleGroup = null
            ),
            DatePeriodDomain(
                position = 2,
                date = ModelStateOutFieldText(valueText = "", isError = true, errorMessage = ModelCodeInputs.SP_NOT_OPTION),
                partialCycleGroup = null
            )
        )

        // When
        val result: ModelStateOutFieldText = useCase.validateAdapterError(periods)

        // Then
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.SP_NOT_OPTION, result.errorMessage)
    }

    @Test
    fun `validateAdapterError retorna formato correcto cuando no hay errores`() {
        // Given
        val periods = listOf(
            DatePeriodDomain(
                position = 1,
                date = ModelStateOutFieldText(valueText = "2024-01-01", isError = false, errorMessage = ""),
                partialCycleGroup = null
            ),
            DatePeriodDomain(
                position = 2,
                date = ModelStateOutFieldText(valueText = "2024-02-01", isError = false, errorMessage = ""),
                partialCycleGroup = null
            )
        )

        // When
        val result: ModelStateOutFieldText = useCase.validateAdapterError(periods)

        // Then
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    @Test
    fun `validateAdapterError retorna formato correcto cuando la lista es null`() {
        // When
        val result: ModelStateOutFieldText = useCase.validateAdapterError(null)

        // Then
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }
}
