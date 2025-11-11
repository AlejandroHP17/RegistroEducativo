package com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.assignment

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.usecase.evaluation.GetDatesActivePartialUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * Tests para [com.mx.liftechnology.domain.usecase.evaluation.GetDatesActivePartialUseCase].
 * Verifica que el caso de uso recupere correctamente el rango de fechas desde las preferencias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetDatesActivePartialUseCaseTest {

    private lateinit var getDatesActivePartialUseCase: GetDatesActivePartialUseCase
    private val preferenceUseCase: PreferenceUseCase = mockk()

    @Before
    fun setUp() {
        getDatesActivePartialUseCase = GetDatesActivePartialUseCase(preferenceUseCase)
    }

    /**
     * Test para verificar que se devuelve el rango de fechas cuando está guardado en las preferencias.
     */
    @Test
    fun `invoke cuando existe un rango de fechas lo devuelve correctamente`() = runBlocking {
        // Preparamos el mock
        val dateRange = "2024-01-01/2024-02-28"
        coEvery { preferenceUseCase.getPreferenceString(ModelPreference.RANGE_DATES_PARTIAL) } returns dateRange

        // Ejecutamos el caso de uso
        val result = getDatesActivePartialUseCase.invoke()

        // Verificamos el resultado
        assertEquals(dateRange, result)
    }

    /**
     * Test para verificar que se devuelve nulo cuando no hay un rango de fechas guardado.
     */
    @Test
    fun `invoke cuando no existe un rango de fechas devuelve nulo`() = runBlocking {
        // Preparamos el mock
        coEvery { preferenceUseCase.getPreferenceString(ModelPreference.RANGE_DATES_PARTIAL) } returns null

        // Ejecutamos el caso de uso
        val result = getDatesActivePartialUseCase.invoke()

        // Verificamos el resultado
        assertNull(result)
    }
}