package com.mx.liftechnology.domain.model.menu

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPartial
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests para la función de extensión [ListPartialToConvertModelDialogGroupPartialDomains].
 * Esta clase contiene los tests unitarios para verificar que la conversión del modelo de red al modelo de dominio funciona correctamente.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ModelDialogGroupPartialDomainTest {

    /**
     * Test para verificar que una lista de [ResponseGetPartial] se convierte correctamente a una lista de [ModelDialogGroupPartialDomain].
     */
    @Test
    fun `ListPartialToConvertModelDialogGroupPartialDomains con lista valida`() {
        // Preparamos los datos de entrada
        val responseList = listOf(
            ResponseGetPartial(1, "Parcial 1", "2024-01-01", "2024-02-01", 101),
            ResponseGetPartial(2, "Parcial 2", "2024-02-02", "2024-03-02", 102)
        )

        // Ejecutamos la función de extensión
        val result = responseList.ListPartialToConvertModelDialogGroupPartialDomains

        // Verificamos el resultado
        assertEquals(2, result.size)
        assertEquals("Parcial 1", result[0].name)
        assertEquals("2024-01-01", result[0].startDate)
    }

    /**
     * Test para verificar que una lista nula o vacía se maneja correctamente.
     */
    @Test
    fun `ListPartialToConvertModelDialogGroupPartialDomains con lista nula o vacia`() {
        // Preparamos los datos de entrada
        val nullList: List<ResponseGetPartial>? = null
        val emptyList = emptyList<ResponseGetPartial>()

        // Ejecutamos la función de extensión
        val resultFromNull = nullList.ListPartialToConvertModelDialogGroupPartialDomains
        val resultFromEmpty = emptyList.ListPartialToConvertModelDialogGroupPartialDomains

        // Verificamos el resultado
        assertEquals(0, resultFromNull.size)
        assertEquals(0, resultFromEmpty.size)
    }
}