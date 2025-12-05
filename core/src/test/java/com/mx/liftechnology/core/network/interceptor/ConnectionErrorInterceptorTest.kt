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
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Tests unitarios para [ConnectionErrorInterceptor].
 * 
 * @author Pelkidev
 * @version 1.0.0
 */
class ConnectionErrorInterceptorTest {

    private lateinit var interceptor: ConnectionErrorInterceptor
    private lateinit var request: Request

    @Before
    fun setup() {
        interceptor = ConnectionErrorInterceptor()
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

        val chain = mockChain(request, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertTrue(result.isSuccessful)
        assertEquals(200, result.code)
    }

    @Test
    fun `intercept propagates IOException`() {
        // Given
        val ioException = IOException("Connection failed")
        val chain = mockChainWithException(request, ioException)

        // When & Then
        try {
            interceptor.intercept(chain)
            org.junit.Assert.fail("Expected IOException to be thrown")
        } catch (e: IOException) {
            assertEquals("Connection failed", e.message)
        }
    }

    @Test
    fun `intercept propagates SocketTimeoutException`() {
        // Given
        val timeoutException = SocketTimeoutException("Read timed out")
        val chain = mockChainWithException(request, timeoutException)

        // When & Then
        try {
            interceptor.intercept(chain)
            org.junit.Assert.fail("Expected SocketTimeoutException to be thrown")
        } catch (e: SocketTimeoutException) {
            assertEquals("Read timed out", e.message)
        }
    }

    @Test
    fun `intercept propagates UnknownHostException`() {
        // Given
        val unknownHostException = UnknownHostException("Unable to resolve host")
        val chain = mockChainWithException(request, unknownHostException)

        // When & Then
        try {
            interceptor.intercept(chain)
            org.junit.Assert.fail("Expected UnknownHostException to be thrown")
        } catch (e: UnknownHostException) {
            assertEquals("Unable to resolve host", e.message)
        }
    }

    @Test
    fun `intercept handles response with error code`() {
        // Given
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(500)
            .message("Internal Server Error")
            .body("Error".toResponseBody("application/json".toMediaType()))
            .build()

        val chain = mockChain(request, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        // ConnectionErrorInterceptor no modifica respuestas, solo maneja IOException
        assertFalse(result.isSuccessful)
        assertEquals(500, result.code)
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
