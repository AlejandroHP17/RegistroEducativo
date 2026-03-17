package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseGetListWorkType
import com.mx.liftechnology.core.network.api.ResponseGetWorkType
import com.mx.liftechnology.core.network.api.ResponseWorkTypeDetail
import com.mx.liftechnology.data.mapper.WorkTypeMapper.toWorkTypeByFormativeFieldDomain
import com.mx.liftechnology.data.mapper.WorkTypeMapper.toWorkTypeDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests para [WorkTypeMapper].
 * 
 * Verifica que todos los mappers de tipos de trabajo funcionen correctamente.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class WorkTypeMapperTest {

    // ========== Tests para toWorkTypeDomain ==========

    @Test
    fun `toWorkTypeDomain con lista completa mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGetListWorkType(
                workTypeId = 1,
                name = "Examen"
            ),
            ResponseGetListWorkType(
                workTypeId = 2,
                name = "Tarea"
            )
        )

        // When
        val result = responseList.toWorkTypeDomain()

        // Then
        assertEquals(2, result.size)
        assertEquals(1, result[0].workTypeId)
        assertEquals("Examen", result[0].name)

        assertEquals(2, result[1].workTypeId)
        assertEquals("Tarea", result[1].name)
    }

    @Test
    fun `toWorkTypeDomain con lista vacía retorna lista vacía`() {
        // Given
        val responseList = emptyList<ResponseGetListWorkType>()

        // When
        val result = responseList.toWorkTypeDomain()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `toWorkTypeDomain con valores nulos mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGetListWorkType(
                workTypeId = 1,
                name = null
            )
        )

        // When
        val result = responseList.toWorkTypeDomain()

        // Then
        assertEquals(1, result.size)
        assertEquals(1, result[0].workTypeId)
        assertNull(result[0].name)
    }

    // ========== Tests para toWorkTypeByFormativeFieldDomain ==========

    @Test
    fun `toWorkTypeByFormativeFieldDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseGetWorkType(
            formativeFieldName = "Matemáticas",
            formativeFieldId = 1,
            workTypes = listOf(
                ResponseWorkTypeDetail(
                    workTypeName = "Examen",
                    workTypeId = 1,
                    evaluationWeight = 50.0
                )
            )
        )

        // When
        val result = response.toWorkTypeByFormativeFieldDomain()

        // Then
        assertNotNull(result)
        assertEquals("Matemáticas", result?.formativeFieldName)
        assertEquals(1, result?.formativeFieldId)
        assertEquals(1, result?.workTypes?.size)
        assertEquals("Examen", result?.workTypes?.get(0)?.workTypeName)
        assertEquals(1, result?.workTypes?.get(0)?.workTypeId)
        assertEquals(50.0, result?.workTypes?.get(0)?.evaluationWeight, 0.01)
    }

    @Test
    fun `toWorkTypeByFormativeFieldDomain con respuesta null retorna null`() {
        // Given
        val response: ResponseGetWorkType? = null

        // When
        val result = response.toWorkTypeByFormativeFieldDomain()

        // Then
        assertNull(result)
    }

    @Test
    fun `toWorkTypeByFormativeFieldDomain con lista vacía mapea correctamente`() {
        // Given
        val response = ResponseGetWorkType(
            formativeFieldName = "Matemáticas",
            formativeFieldId = 1,
            workTypes = emptyList()
        )

        // When
        val result = response.toWorkTypeByFormativeFieldDomain()

        // Then
        assertNotNull(result)
        assertTrue(result?.workTypes?.isEmpty() ?: false)
    }

    @Test
    fun `toWorkTypeByFormativeFieldDomain con elementos null los omite`() {
        // Given
        val response = ResponseGetWorkType(
            formativeFieldName = "Matemáticas",
            formativeFieldId = 1,
            workTypes = listOf(null, null)
        )

        // When
        val result = response.toWorkTypeByFormativeFieldDomain()

        // Then
        assertNotNull(result)
        assertTrue(result?.workTypes?.isEmpty() ?: false)
    }

    @Test
    fun `toWorkTypeByFormativeFieldDomain con valores nulos mapea correctamente`() {
        // Given
        val response = ResponseGetWorkType(
            formativeFieldName = null,
            formativeFieldId = null,
            workTypes = emptyList()
        )

        // When
        val result = response.toWorkTypeByFormativeFieldDomain()

        // Then
        assertNotNull(result)
        assertNull(result?.formativeFieldName)
        assertNull(result?.formativeFieldId)
    }
}
