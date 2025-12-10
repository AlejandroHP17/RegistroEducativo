package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseDataUser
import com.mx.liftechnology.core.network.api.ResponseLogin
import com.mx.liftechnology.core.network.api.ResponseRegisterUser
import com.mx.liftechnology.data.mapper.AuthMapper.toLoginDomain
import com.mx.liftechnology.data.mapper.AuthMapper.toRegisterUserDomain
import com.mx.liftechnology.data.mapper.AuthMapper.toUserDomain
import com.mx.liftechnology.domain.model.auth.LoginDomain
import com.mx.liftechnology.domain.model.auth.RegisterUserDomain
import com.mx.liftechnology.domain.model.auth.UserDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Tests para [AuthMapper].
 * 
 * Verifica que todos los mappers de autenticación funcionen correctamente con diferentes escenarios:
 * - Datos completos
 * - Datos con valores nulos
 * - Datos con valores vacíos
 * - Respuestas nulas
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class AuthMapperTest {

    // ========== Tests para toUserDomain ==========

    @Test
    fun `toUserDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseDataUser(
            email = "test@example.com",
            name = "Juan",
            lastName = "Pérez",
            phone = "1234567890",
            isActive = true,
            userId = 1,
            accessLevelId = 2,
            accessCodeId = 1,
            createdAt = "asdasd"
        )

        // When
        val result = response.toUserDomain()

        // Then
        assertEquals("test@example.com", result.email)
        assertEquals("Juan", result.name)
        assertEquals("Pérez", result.lastName)
        assertEquals("1234567890", result.phone)
        assertEquals(true, result.isActive)
        assertEquals(1, result.userId)
        assertEquals(2, result.accessLevelId)
    }

    @Test
    fun `toUserDomain con valores nulos mapea correctamente`() {
        // Given
        val response = ResponseDataUser(
            email = null,
            name = null,
            lastName = null,
            phone = null,
            isActive = false,
            userId = null,
            accessLevelId = null
        )

        // When
        val result = response.toUserDomain()

        // Then
        assertNull(result.email)
        assertNull(result.name)
        assertNull(result.lastName)
        assertNull(result.phone)
        assertEquals(false, result.isActive)
        assertNull(result.userId)
        assertNull(result.accessLevelId)
    }

    @Test
    fun `toUserDomain con valores vacíos mapea correctamente`() {
        // Given
        val response = ResponseDataUser(
            email = "",
            name = "",
            lastName = "",
            phone = "",
            isActive = false,
            userId = 0,
            accessLevelId = 0
        )

        // When
        val result = response.toUserDomain()

        // Then
        assertEquals("", result.email)
        assertEquals("", result.name)
        assertEquals("", result.lastName)
        assertEquals("", result.phone)
        assertEquals(false, result.isActive)
        assertEquals(0, result.userId)
        assertEquals(0, result.accessLevelId)
    }

    // ========== Tests para toLoginDomain ==========

    @Test
    fun `toLoginDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseLogin(
            accessToken = "access_token_123",
            refreshToken = "refresh_token_456",
            tokenType = "Bearer"
        )

        // When
        val result = response.toLoginDomain()

        // Then
        assertNotNull(result)
        assertEquals("access_token_123", result?.accessToken)
        assertEquals("refresh_token_456", result?.refreshToken)
        assertEquals("Bearer", result?.tokenType)
    }

    @Test
    fun `toLoginDomain con valores nulos mapea correctamente`() {
        // Given
        val response = ResponseLogin(
            accessToken = null,
            refreshToken = null,
            tokenType = null
        )

        // When
        val result = response.toLoginDomain()

        // Then
        assertNotNull(result)
        assertNull(result?.accessToken)
        assertNull(result?.refreshToken)
        assertNull(result?.tokenType)
    }

    @Test
    fun `toLoginDomain con respuesta null retorna null`() {
        // Given
        val response: ResponseLogin? = null

        // When
        val result = response.toLoginDomain()

        // Then
        assertNull(result)
    }

    @Test
    fun `toLoginDomain con valores vacíos mapea correctamente`() {
        // Given
        val response = ResponseLogin(
            accessToken = "",
            refreshToken = "",
            tokenType = ""
        )

        // When
        val result = response.toLoginDomain()

        // Then
        assertNotNull(result)
        assertEquals("", result?.accessToken)
        assertEquals("", result?.refreshToken)
        assertEquals("", result?.tokenType)
    }

    // ========== Tests para toRegisterUserDomain ==========

    @Test
    fun `toRegisterUserDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseRegisterUser(
            email = "test@example.com",
            firstName = "Juan",
            lastName = "Pérez",
            accessLevel = 2,
            isActive = true,
            id = 1
        )

        // When
        val result = response.toRegisterUserDomain()

        // Then
        assertEquals("test@example.com", result.email)
        assertEquals("Juan", result.firstName)
        assertEquals("Pérez", result.lastName)
        assertEquals(2, result.accessLevel)
        assertEquals(true, result.isActive)
        assertEquals(1, result.userId)
    }

    @Test
    fun `toRegisterUserDomain con valores nulos mapea correctamente`() {
        // Given
        val response = ResponseRegisterUser(
            email = null,
            firstName = null,
            lastName = null,
            accessLevel = null,
            isActive = false,
            id = null
        )

        // When
        val result = response.toRegisterUserDomain()

        // Then
        assertNull(result.email)
        assertNull(result.firstName)
        assertNull(result.lastName)
        assertNull(result.accessLevel)
        assertEquals(false, result.isActive)
        assertNull(result.userId)
    }

    @Test
    fun `toRegisterUserDomain con valores vacíos mapea correctamente`() {
        // Given
        val response = ResponseRegisterUser(
            email = "",
            firstName = "",
            lastName = "",
            accessLevel = 0,
            isActive = false,
            id = 0
        )

        // When
        val result = response.toRegisterUserDomain()

        // Then
        assertEquals("", result.email)
        assertEquals("", result.firstName)
        assertEquals("", result.lastName)
        assertEquals(0, result.accessLevel)
        assertEquals(false, result.isActive)
        assertEquals(0, result.userId)
    }

    @Test
    fun `toRegisterUserDomain con userId mapeado desde id`() {
        // Given
        val response = ResponseRegisterUser(
            email = "test@example.com",
            firstName = "Juan",
            lastName = "Pérez",
            accessLevel = 1,
            isActive = true,
            id = 999
        )

        // When
        val result = response.toRegisterUserDomain()

        // Then
        assertEquals(999, result.userId)
        assertEquals(response.id, result.userId)
    }
}
