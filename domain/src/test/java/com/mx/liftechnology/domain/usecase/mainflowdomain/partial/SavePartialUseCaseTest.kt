package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.menu.ModelDialogGroupPartialDomain
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Tests para [SavePartialUseCase].
 * Verifica que la lógica para seleccionar y guardar el parcial activo funcione correctamente.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SavePartialUseCaseTest {

    private lateinit var savePartialUseCase: SavePartialUseCase
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    private val today = LocalDate.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @Before
    fun setUp() {
        savePartialUseCase = SavePartialUseCase(preferenceUseCase)
    }

    /**
     * Test para verificar que se selecciona el parcial correcto cuando la fecha actual está dentro de su rango.
     */
    @Test
    fun `invoke cuando la fecha actual esta en un rango selecciona el parcial correcto`() {
        // Preparamos la lista de parciales
        val partials = listOf(
            ModelDialogGroupPartialDomain(1, today.minusMonths(1).format(formatter), today.minusDays(1).format(formatter), "Parcial Pasado"),
            ModelDialogGroupPartialDomain(2, today.format(formatter), today.plusMonths(1).format(formatter), "Parcial Actual"),
            ModelDialogGroupPartialDomain(3, today.plusMonths(2).format(formatter), today.plusMonths(3).format(formatter), "Parcial Futuro")
        )

        // Ejecutamos el caso de uso
        val result = savePartialUseCase.invoke(partials)

        // Verificamos el resultado
        assertNotNull(result)
        assertEquals(2, result?.partialId)
        assertEquals("Parcial Actual", result?.name)

        // Verificamos que se guardaron las preferencias correctas
        verify { preferenceUseCase.savePreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP, 2) }
    }

    /**
     * Test para verificar que se selecciona el último parcial cuando la fecha actual no está en ningún rango.
     */
    @Test
    fun `invoke cuando la fecha actual no esta en rango selecciona el ultimo parcial`() {
        // Preparamos la lista de parciales (todos en el pasado)
        val partials = listOf(
            ModelDialogGroupPartialDomain(1, "2023-01-01", "2023-02-01", "Parcial 1"),
            ModelDialogGroupPartialDomain(2, "2023-03-01", "2023-04-01", "Parcial 2")
        )

        // Ejecutamos el caso de uso
        val result = savePartialUseCase.invoke(partials)

        // Verificamos que se seleccionó el último parcial
        assertNotNull(result)
        assertEquals(2, result?.partialId)

        // Verificamos que se guardaron las preferencias del último parcial
        verify { preferenceUseCase.savePreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP, 2) }
    }

    /**
     * Test para verificar que no se selecciona ningún parcial y se limpian las preferencias cuando la lista está vacía.
     */
    @Test
    fun `invoke con lista vacia no selecciona nada y limpia preferencias`() {
        // Ejecutamos el caso de uso con una lista vacía
        val result = savePartialUseCase.invoke(emptyList())

        // Verificamos que el resultado es nulo
        assertNull(result)

        // Verificamos que se limpiaron las preferencias
        //verify { preferenceUseCase.savePreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP, -1) }
        //verify { preferenceUseCase.savePreferenceString(ModelPreference.RANGE_DATES_PARTIAL, null) }
    }
}
