package com.mx.liftechnology.domain.model.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListSubject
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests para la función de extensión [toModelSubjectList].
 * Verifica que la conversión del modelo de red al de dominio sea correcta.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ModelFormatSubjectDomainTest {

    /**
     * Test para verificar que una lista de [ResponseGetListSubject] se convierte correctamente a una lista de [ModelFormatSubjectDomain].
     */
    @Test
    fun `toModelSubjectList con lista valida`() {
        // Preparamos los datos de entrada
        val responseList = listOf(
            ResponseGetListSubject(1, "Matemáticas"),
            ResponseGetListSubject(2, "Español")
        )

        // Ejecutamos la función de extensión
        val result = responseList.toModelSubjectList()

        // Verificamos el resultado
        assertEquals(2, result.size)
        assertEquals("Matemáticas", result[0].name)
        assertEquals(1, result[0].subjectId)
    }

    /**
     * Test para verificar que una lista nula o vacía se maneja correctamente, devolviendo una lista vacía.
     */
    @Test
    fun `toModelSubjectList con lista nula o vacia`() {
        // Preparamos los datos de entrada
        val nullList: List<ResponseGetListSubject>? = null
        val emptyList = emptyList<ResponseGetListSubject>()

        // Ejecutamos la función de extensión
        val resultFromNull = nullList.toModelSubjectList()
        val resultFromEmpty = emptyList.toModelSubjectList()

        // Verificamos el resultado
        assertEquals(0, resultFromNull.size)
        assertEquals(0, resultFromEmpty.size)
    }

    /**
     * Test para verificar que los elementos nulos dentro de una lista son omitidos durante la conversión.
     */
    @Test
    fun `toModelSubjectList con elementos nulos en la lista`() {
        // Preparamos los datos de entrada
        val listWithNulls = listOf(
            ResponseGetListSubject(1, "Matemáticas"),
            null,
            ResponseGetListSubject(2, "Español")
        )

        // Ejecutamos la función de extensión
        val result = listWithNulls.toModelSubjectList()

        // Verificamos el resultado
        assertEquals(2, result.size)
    }
}