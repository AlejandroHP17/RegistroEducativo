package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseFormativeFieldBulk
import com.mx.liftechnology.core.network.api.ResponseFormativeFields
import com.mx.liftechnology.core.network.api.ResponseGetListFormativeField
import com.mx.liftechnology.core.network.api.ResponseGetListWotyFofi
import com.mx.liftechnology.core.network.api.ResponseWorkTypes
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toDomain
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toFormativeFieldDomain
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toWotyFofiDomain
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.WotyFofiDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests para [FormativeFieldMapper].
 * 
 * Verifica que todos los mappers de campos formativos funcionen correctamente.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class FormativeFieldMapperTest {

    // ========== Tests para toFormativeFieldDomain (List) ==========

    @Test
    fun `toFormativeFieldDomain con lista completa mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGetListFormativeField(
                formativeFieldId = 1,
                name = "Matemáticas",
                code = "MAT001"
            ),
            ResponseGetListFormativeField(
                formativeFieldId = 2,
                name = "Español",
                code = "ESP001"
            )
        )

        // When
        val result = responseList.toFormativeFieldDomain()

        // Then
        assertEquals(2, result.size)
        assertEquals("Matemáticas", result[0].name)
        assertEquals("MAT001", result[0].code)
        assertEquals(1, result[0].formativeFieldID)

        assertEquals("Español", result[1].name)
        assertEquals("ESP001", result[1].code)
        assertEquals(2, result[1].formativeFieldID)
    }

    @Test
    fun `toFormativeFieldDomain con lista vacía retorna lista vacía`() {
        // Given
        val responseList = emptyList<ResponseGetListFormativeField>()

        // When
        val result = responseList.toFormativeFieldDomain()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `toFormativeFieldDomain con valores nulos mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGetListFormativeField(
                formativeFieldId = 1,
                name = null,
                code = null
            )
        )

        // When
        val result = responseList.toFormativeFieldDomain()

        // Then
        assertEquals(1, result.size)
        assertNull(result[0].name)
        assertNull(result[0].code)
        assertEquals(1, result[0].formativeFieldID)
    }

    // ========== Tests para toWotyFofiDomain ==========

    @Test
    fun `toWotyFofiDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseGetListWotyFofi(
            formativeFields = listOf(
                ResponseFormativeFields(
                    formativeFieldId = 1,
                    formativeFieldName = "Matemáticas",
                    code = "MAT001",
                    listWorkTypes = listOf(
                        ResponseWorkTypes(
                            workTypeId = 1,
                            workTypeName = "Examen",
                            evaluationWeight = 50.0
                        )
                    )
                )
            )
        )

        // When
        val result = response.toWotyFofiDomain()

        // Then
        assertEquals(1, result.formativeFields.size)
        assertEquals(1, result.formativeFields[0].formativeFieldId)
        assertEquals("Matemáticas", result.formativeFields[0].formativeFieldName)
        assertEquals("MAT001", result.formativeFields[0].code)
        assertEquals(1, result.formativeFields[0].listWorkTypes.size)
        assertEquals(1, result.formativeFields[0].listWorkTypes[0].workTypeId)
        assertEquals("Examen", result.formativeFields[0].listWorkTypes[0].workTypeName)
        assertEquals(50.0, result.formativeFields[0].listWorkTypes[0].evaluationWeight, 0.01)
    }

    @Test
    fun `toWotyFofiDomain con lista vacía mapea correctamente`() {
        // Given
        val response = ResponseGetListWotyFofi(
            formativeFields = emptyList()
        )

        // When
        val result = response.toWotyFofiDomain()

        // Then
        assertTrue(result.formativeFields.isEmpty())
    }

    @Test
    fun `toWotyFofiDomain con elementos null los omite`() {
        // Given
        val response = ResponseGetListWotyFofi(
            formativeFields = listOf(null, null)
        )

        // When
        val result = response.toWotyFofiDomain()

        // Then
        assertTrue(result.formativeFields.isEmpty())
    }

    // ========== Tests para toFormativeFieldDomain (Bulk) ==========

    @Test
    fun `toFormativeFieldDomain con ResponseFormativeFieldBulk mapea correctamente`() {
        // Given
        val response = ResponseFormativeFieldBulk(
            formativeFieldsId = 1,
            formativeFieldsName = "Matemáticas",
            formativeFieldsCode = "MAT001"
        )

        // When
        val result = response.toFormativeFieldDomain()

        // Then
        assertEquals("Matemáticas", result.name)
        assertEquals("MAT001", result.code)
        assertEquals(1, result.formativeFieldID)
    }

    @Test
    fun `toFormativeFieldDomain con ResponseFormativeFieldBulk valores nulos mapea correctamente`() {
        // Given
        val response = ResponseFormativeFieldBulk(
            formativeFieldsId = 0,
            formativeFieldsName = null,
            formativeFieldsCode = null
        )

        // When
        val result = response.toFormativeFieldDomain()

        // Then
        assertNull(result.name)
        assertNull(result.code)
        assertEquals(0, result.formativeFieldID)
    }

    // ========== Tests para toDomain ==========

    @Test
    fun `toDomain con string normal retorna el mismo string`() {
        // Given
        val input = "Test String"

        // When
        val result = input.toDomain()

        // Then
        assertEquals("Test String", result)
    }

    @Test
    fun `toDomain con string vacío retorna string vacío`() {
        // Given
        val input = ""

        // When
        val result = input.toDomain()

        // Then
        assertEquals("", result)
    }
}
