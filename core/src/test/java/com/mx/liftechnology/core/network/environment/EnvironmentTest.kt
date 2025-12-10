package com.mx.liftechnology.core.network.environment

import android.os.Build
import com.mx.liftechnology.core.BuildConfig
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests unitarios para [Environment].
 * 
 * Nota: Algunos tests requieren acceso a BuildConfig que puede variar según el build type.
 * 
 * @author Pelkidev
 * @version 1.0.0
 */
class EnvironmentTest {

    @Before
    fun setup() {
        // Setup inicial si es necesario
    }

    @After
    fun tearDown() {
        // Cleanup si es necesario
    }

    @Test
    fun `URL_BASE returns a non-empty string`() {
        // When
        val url = Environment.URL_BASE

        // Then
        assertNotNull(url)
        assertTrue(url.isNotEmpty())
        assertTrue(url.startsWith("http://") || url.startsWith("https://"))
    }

    @Test
    fun `URL_BASE ends with api slash`() {
        // When
        val url = Environment.URL_BASE

        // Then
        assertTrue(url.endsWith("/") || url.endsWith("/api/"))
    }

    @Test
    fun `API_VERSION returns a non-empty string`() {
        // When
        val apiVersion = Environment.API_VERSION

        // Then
        assertNotNull(apiVersion)
        assertTrue(apiVersion.isNotEmpty())
    }

    @Test
    fun `API_VERSION matches BuildConfig value`() {
        // When
        val apiVersion = Environment.API_VERSION

        // Then
        assertEquals(BuildConfig.API_VERSION, apiVersion)
    }

    @Test
    fun `END_POINT_LOGIN is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_LOGIN

        // Then
        assertEquals("auth/login", endpoint)
    }

    @Test
    fun `END_POINT_GET_DATA is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_GET_DATA

        // Then
        assertEquals("auth/me", endpoint)
    }

    @Test
    fun `END_POINT_REGISTER is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_REGISTER

        // Then
        assertEquals("auth/register", endpoint)
    }

    @Test
    fun `END_POINT_REFRESH is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_REFRESH

        // Then
        assertEquals("auth/refresh", endpoint)
    }

    @Test
    fun `END_POINT_REGISTER_SCHOOL_CYCLE is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_REGISTER_SCHOOL_CYCLE

        // Then
        assertEquals("cycles/", endpoint)
    }

    @Test
    fun `END_POINT_REGISTER_PARTIAL is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_REGISTER_PARTIAL

        // Then
        assertEquals("partials/", endpoint)
    }

    @Test
    fun `END_POINT_REGISTER_STUDENT is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_REGISTER_STUDENT

        // Then
        assertEquals("students/", endpoint)
    }

    @Test
    fun `END_POINT_REGISTER_FORMATIVE_FIELDS_BULK is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_REGISTER_FORMATIVE_FIELDS_BULK

        // Then
        assertEquals("formative-fields/bulk", endpoint)
    }

    @Test
    fun `END_POINT_GET_PARTIAL is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_GET_PARTIAL

        // Then
        assertEquals("partials/", endpoint)
    }

    @Test
    fun `END_POINT_GET_STUDENT is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_GET_STUDENT

        // Then
        assertEquals("students/", endpoint)
    }

    @Test
    fun `END_POINT_GET_FORMATIVE_FIELDS is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_GET_FORMATIVE_FIELDS

        // Then
        assertEquals("formative-fields/", endpoint)
    }

    @Test
    fun `END_POINT_GET_WORK_TYPE is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_GET_WORK_TYPE

        // Then
        assertEquals("work-types/", endpoint)
    }

    @Test
    fun `END_POINT_DELETE_STUDENT is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_DELETE_STUDENT

        // Then
        assertEquals("students/{student_id}", endpoint)
    }

    @Test
    fun `END_POINT_DELETE_FORMATIVE_FIELDS is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_DELETE_FORMATIVE_FIELDS

        // Then
        assertEquals("formative-fields/{field_id}", endpoint)
    }

    @Test
    fun `END_POINT_EDIT_STUDENT is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_EDIT_STUDENT

        // Then
        assertEquals("students/{student_id}", endpoint)
    }

    @Test
    fun `END_POINT_GET_CCT is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_GET_CCT

        // Then
        assertEquals("schools/{cct}", endpoint)
    }

    @Test
    fun `END_POINT_GET_CYCLE_SCHOOL is correctly defined`() {
        // When
        val endpoint = Environment.END_POINT_GET_CYCLE_SCHOOL

        // Then
        assertEquals("cycles/", endpoint)
    }
}
