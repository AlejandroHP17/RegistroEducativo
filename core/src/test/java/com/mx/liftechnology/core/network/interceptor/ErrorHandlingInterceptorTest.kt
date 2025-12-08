package com.mx.liftechnology.core.network.interceptor

import io.mockk.every
import io.mockk.mockk
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Tests unitarios para [ErrorHandlingInterceptor].
 * 
 * @author Pelkidev
 * @version 1.0.0
 */
class ErrorHandlingInterceptorTest {

    private lateinit var interceptor: ErrorHandlingInterceptor
    private lateinit var chain: Interceptor.Chain
    private lateinit var request: Request

    @Before
    fun setup() {
        interceptor = ErrorHandlingInterceptor()
        request = Request.Builder()
            .url("https://api.example.com/test")
            .get()
            .build()
    }

    @Test
    fun `intercept passes through successful response`() {
        // Given
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body("Success".toResponseBody("text/plain".toMediaType()))
            .build()

        chain = mockChain(request, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertTrue(result.isSuccessful)
        assertEquals(200, result.code)
    }

    @Test
    fun `intercept handles 400 Bad Request error`() {
        // Given
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(400)
            .message("Bad Request")
            .body("Error message".toResponseBody("application/json".toMediaType()))
            .build()

        chain = mockChain(request, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertFalse(result.isSuccessful)
        assertEquals(400, result.code)
    }

    @Test
    fun `intercept handles 404 Not Found error`() {
        // Given
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(404)
            .message("Not Found")
            .body("Not Found".toResponseBody("application/json".toMediaType()))
            .build()

        chain = mockChain(request, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertFalse(result.isSuccessful)
        assertEquals(404, result.code)
    }

    @Test
    fun `intercept handles 500 Server Error`() {
        // Given
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(500)
            .message("Internal Server Error")
            .body("Server Error".toResponseBody("application/json".toMediaType()))
            .build()

        chain = mockChain(request, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertFalse(result.isSuccessful)
        assertEquals(500, result.code)
    }

    @Test
    fun `intercept handles 401 Unauthorized error`() {
        // Given
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(401)
            .message("Unauthorized")
            .body("Unauthorized".toResponseBody("application/json".toMediaType()))
            .build()

        chain = mockChain(request, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertFalse(result.isSuccessful)
        assertEquals(401, result.code)
    }

    @Test
    fun `intercept handles 403 Forbidden error`() {
        // Given
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(403)
            .message("Forbidden")
            .body("Forbidden".toResponseBody("application/json".toMediaType()))
            .build()

        chain = mockChain(request, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertFalse(result.isSuccessful)
        assertEquals(403, result.code)
    }

    @Test
    fun `intercept handles 429 Too Many Requests error`() {
        // Given
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(429)
            .message("Too Many Requests")
            .body("Too Many Requests".toResponseBody("application/json".toMediaType()))
            .build()

        chain = mockChain(request, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertFalse(result.isSuccessful)
        assertEquals(429, result.code)
    }

    @Test
    fun `intercept propagates IOException`() {
        // Given
        val ioException = IOException("Connection failed")
        chain = mockChainWithException(request, ioException)

        // When & Then
        try {
            interceptor.intercept(chain)
            fail("Expected IOException to be thrown")
        } catch (e: IOException) {
            assertEquals("Connection failed", e.message)
        }
    }

    @Test
    fun `intercept handles response with empty body`() {
        // Given
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(404)
            .message("Not Found")
            .body("".toResponseBody("application/json".toMediaType()))
            .build()

        chain = mockChain(request, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertFalse(result.isSuccessful)
        assertEquals(404, result.code)
    }

    private fun mockChain(request: Request, response: Response): Interceptor.Chain {
        return io.mockk.mockk<Interceptor.Chain> {
            every { this@mockk.request() } returns request
            every { this@mockk.proceed(request) } returns response
        }
    }

    private fun mockChainWithException(request: Request, exception: IOException): Interceptor.Chain {
        return io.mockk.mockk<Interceptor.Chain> {
            every { this@mockk.request() } returns request
            every { this@mockk.proceed(request) } throws exception
        }
    }
}
