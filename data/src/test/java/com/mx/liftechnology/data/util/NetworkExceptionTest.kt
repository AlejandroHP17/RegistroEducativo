package com.mx.liftechnology.data.util

import com.mx.liftechnology.core.util.models.NetworkModelError
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

/**
 * Tests para [NetworkException].
 * 
 * Verifica que todas las excepciones se mapeen correctamente a los tipos de error correspondientes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class NetworkExceptionTest {

    @Test
    fun `handleException con UnknownHostException retorna NO_INTERNET`() {
        // Given
        val exception = UnknownHostException("No se puede resolver el host")

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.NO_INTERNET, result)
    }

    @Test
    fun `handleException con ConnectException retorna NO_INTERNET`() {
        // Given
        val exception = ConnectException("No se puede establecer conexión")

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.NO_INTERNET, result)
    }

    @Test
    fun `handleException con SocketTimeoutException retorna TIMEOUT`() {
        // Given
        val exception = SocketTimeoutException("Timeout de conexión")

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.TIMEOUT, result)
    }

    @Test
    fun `handleException con HttpException código 400 retorna BAD_REQUEST`() {
        // Given
        val responseBody = "Error".toResponseBody("application/json".toMediaType())
        val response = Response.error<Any>(400, responseBody)
        val exception = HttpException(response)

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.BAD_REQUEST, result)
    }

    @Test
    fun `handleException con HttpException código 401 retorna UNAUTHORIZED`() {
        // Given
        val responseBody = "Unauthorized".toResponseBody("application/json".toMediaType())
        val response = Response.error<Any>(401, responseBody)
        val exception = HttpException(response)

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.UNAUTHORIZED, result)
    }

    @Test
    fun `handleException con HttpException código 403 retorna WITHOUT_ACCESS`() {
        // Given
        val responseBody = "Forbidden".toResponseBody("application/json".toMediaType())
        val response = Response.error<Any>(403, responseBody)
        val exception = HttpException(response)

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.WITHOUT_ACCESS, result)
    }

    @Test
    fun `handleException con HttpException código 404 retorna NOT_FOUND`() {
        // Given
        val responseBody = "Not Found".toResponseBody("application/json".toMediaType())
        val response = Response.error<Any>(404, responseBody)
        val exception = HttpException(response)

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.NOT_FOUND, result)
    }

    @Test
    fun `handleException con HttpException código 409 retorna CONFLICT`() {
        // Given
        val responseBody = "Conflict".toResponseBody("application/json".toMediaType())
        val response = Response.error<Any>(409, responseBody)
        val exception = HttpException(response)

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.CONFLICT, result)
    }

    @Test
    fun `handleException con HttpException código 429 retorna TOO_MANY_REQUESTS`() {
        // Given
        val responseBody = "Too Many Requests".toResponseBody("application/json".toMediaType())
        val response = Response.error<Any>(429, responseBody)
        val exception = HttpException(response)

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.TOO_MANY_REQUESTS, result)
    }

    @Test
    fun `handleException con HttpException código 430 retorna NOT_ACTIVE`() {
        // Given
        val responseBody = "Not Active".toResponseBody("application/json".toMediaType())
        val response = Response.error<Any>(430, responseBody)
        val exception = HttpException(response)

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.NOT_ACTIVE, result)
    }

    @Test
    fun `handleException con HttpException código 500 retorna SERVER_ERROR`() {
        // Given
        val responseBody = "Internal Server Error".toResponseBody("application/json".toMediaType())
        val response = Response.error<Any>(500, responseBody)
        val exception = HttpException(response)

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.SERVER_ERROR, result)
    }

    @Test
    fun `handleException con HttpException código desconocido retorna UNKNOWN`() {
        // Given
        val responseBody = "Error".toResponseBody("application/json".toMediaType())
        val response = Response.error<Any>(503, responseBody)
        val exception = HttpException(response)

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.UNKNOWN, result)
    }

    @Test
    fun `handleException con excepción genérica retorna UNKNOWN`() {
        // Given
        val exception = RuntimeException("Error genérico")

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.UNKNOWN, result)
    }

    @Test
    fun `handleException con NullPointerException retorna UNKNOWN`() {
        // Given
        val exception = NullPointerException("Null pointer")

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.UNKNOWN, result)
    }

    @Test
    fun `handleException con IllegalArgumentException retorna UNKNOWN`() {
        // Given
        val exception = IllegalArgumentException("Argumento inválido")

        // When
        val result = NetworkException.handleException(exception)

        // Then
        assertEquals(NetworkModelError.UNKNOWN, result)
    }
}
