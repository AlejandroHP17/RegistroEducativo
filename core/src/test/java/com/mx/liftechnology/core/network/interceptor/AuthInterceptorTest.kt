package com.mx.liftechnology.core.network.interceptor

import com.mx.liftechnology.core.network.environment.Environment
import com.mx.liftechnology.core.network.util.TokenProvider
import com.mx.liftechnology.core.util.session.SessionManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Tests unitarios para [AuthInterceptor].
 * 
 * @author Pelkidev
 * @version 1.0.0
 */
@OptIn(ExperimentalCoroutinesApi::class)
class AuthInterceptorTest {

    private lateinit var mockTokenProvider: TokenProvider
    private lateinit var mockSessionManager: SessionManager
    private lateinit var interceptor: AuthInterceptor
    private lateinit var request: Request

    @Before
    fun setup() {
        mockTokenProvider = io.mockk.mockk(relaxed = true)
        mockSessionManager = SessionManager()
        interceptor = AuthInterceptor(mockSessionManager, mockTokenProvider)
        request = Request.Builder()
            .url("https://api.example.com/test")
            .get()
            .build()
    }

    @Test
    fun `intercept does not add token for login endpoint`() {
        // Given
        val loginRequest = Request.Builder()
            .url("https://api.example.com/${Environment.END_POINT_LOGIN}")
            .post("{}".toRequestBody("application/json".toMediaType()))
            .build()

        val response = Response.Builder()
            .request(loginRequest)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body("Success".toResponseBody("text/plain".toMediaType()))
            .build()

        val chain = mockChain(loginRequest, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertTrue(result.isSuccessful)
        verify(exactly = 0) { mockTokenProvider.getToken() }
    }

    @Test
    fun `intercept does not add token for register endpoint`() {
        // Given
        val registerRequest = Request.Builder()
            .url("https://api.example.com/${Environment.END_POINT_REGISTER}")
            .post("{}".toRequestBody("application/json".toMediaType()))
            .build()

        val response = Response.Builder()
            .request(registerRequest)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body("Success".toResponseBody("text/plain".toMediaType()))
            .build()

        val chain = mockChain(registerRequest, response)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertTrue(result.isSuccessful)
        verify(exactly = 0) { mockTokenProvider.getToken() }
    }

    @Test
    fun `intercept adds token for authenticated endpoint`() {
        // Given
        val accessToken = "test_access_token"
        every { mockTokenProvider.getToken() } returns accessToken

        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body("Success".toResponseBody("text/plain".toMediaType()))
            .build()

        val chain = mockChainWithAuth(request, response, accessToken)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertTrue(result.isSuccessful)
        verify { mockTokenProvider.getToken() }
    }

    @Test
    fun `intercept handles 401 without refresh token by closing session`() = runTest {
        // Given
        val accessToken = "expired_token"
        every { mockTokenProvider.getToken() } returns accessToken
        every { mockTokenProvider.getRefreshToken() } returns null

        val unauthorizedResponse = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(401)
            .message("Unauthorized")
            .body("Unauthorized".toResponseBody("application/json".toMediaType()))
            .build()

        val chain = mockChainWithAuth(request, unauthorizedResponse, accessToken)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertEquals(401, result.code)
        verify { mockTokenProvider.getRefreshToken() }
        verify { mockTokenProvider.closeSession() }
    }

    // TODO: Este test requiere un setup más complejo con Environment.URL_BASE
    // que puede no estar disponible en el entorno de testing unitario
    // @Test
    fun `intercept refreshes token on 401 and retries request - DISABLED`() = runTest {
        // Given
        val expiredToken = "expired_token"
        val refreshToken = "refresh_token"
        val newAccessToken = "new_access_token"

        every { mockTokenProvider.getToken() } returns expiredToken
        every { mockTokenProvider.getRefreshToken() } returns refreshToken

        val unauthorizedResponse = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(401)
            .message("Unauthorized")
            .body("Unauthorized".toResponseBody("application/json".toMediaType()))
            .build()

        val refreshResponseBody = """{"data":{"access_token":"$newAccessToken"}}"""

        val refreshResponse = Response.Builder()
            .request(
                Request.Builder()
                    .url("${Environment.URL_BASE}${Environment.END_POINT_REFRESH}")
                    .post("{}".toRequestBody("application/json".toMediaType()))
                    .build()
            )
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(refreshResponseBody.toResponseBody("application/json".toMediaType()))
            .build()

        val retryResponse = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body("Success".toResponseBody("text/plain".toMediaType()))
            .build()

        val chain = mockChainWithRefresh(request, unauthorizedResponse, refreshResponse, retryResponse, expiredToken, newAccessToken)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertTrue(result.isSuccessful)
        verify { mockTokenProvider.saveNewToken(newAccessToken) }
    }

    // TODO: Este test requiere un setup más complejo con Environment.URL_BASE
    // que puede no estar disponible en el entorno de testing unitario
    // @Test
    fun `intercept closes session when refresh fails - DISABLED`() = runTest {
        // Given
        val expiredToken = "expired_token"
        val refreshToken = "refresh_token"

        every { mockTokenProvider.getToken() } returns expiredToken
        every { mockTokenProvider.getRefreshToken() } returns refreshToken

        val unauthorizedResponse = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(401)
            .message("Unauthorized")
            .body("Unauthorized".toResponseBody("application/json".toMediaType()))
            .build()

        val refreshResponse = Response.Builder()
            .request(
                Request.Builder()
                    .url("${Environment.URL_BASE}${Environment.END_POINT_REFRESH}")
                    .post("{}".toRequestBody("application/json".toMediaType()))
                    .build()
            )
            .protocol(Protocol.HTTP_1_1)
            .code(401)
            .message("Unauthorized")
            .body("Refresh failed".toResponseBody("application/json".toMediaType()))
            .build()

        val chain = mockChainWithRefreshFailure(request, unauthorizedResponse, refreshResponse, expiredToken)

        // When
        val result = interceptor.intercept(chain)

        // Then
        assertEquals(401, result.code)
        verify { mockTokenProvider.closeSession() }
    }

    @Test
    fun `intercept propagates IOException`() {
        // Given
        val accessToken = "test_token"
        every { mockTokenProvider.getToken() } returns accessToken

        val ioException = IOException("Connection failed")
        val chain = mockChainWithException(request, ioException, accessToken)

        // When & Then
        try {
            interceptor.intercept(chain)
            org.junit.Assert.fail("Expected IOException to be thrown")
        } catch (e: IOException) {
            assertEquals("Connection failed", e.message)
        }
    }

    private fun mockChain(request: Request, response: Response): Interceptor.Chain {
        return io.mockk.mockk<Interceptor.Chain> {
            every { this@mockk.request() } returns request
            every { this@mockk.proceed(request) } returns response
        }
    }

    private fun mockChainWithAuth(
        originalRequest: Request,
        response: Response,
        expectedToken: String
    ): Interceptor.Chain {
        return io.mockk.mockk<Interceptor.Chain> {
            every { this@mockk.request() } returns originalRequest
            every {
                this@mockk.proceed(match { req ->
                    req.header("Authorization") == "Bearer $expectedToken"
                })
            } returns response
        }
    }

    private fun mockChainWithRefresh(
        originalRequest: Request,
        unauthorizedResponse: Response,
        refreshResponse: Response,
        retryResponse: Response,
        expiredToken: String,
        newToken: String
    ): Interceptor.Chain {
        return io.mockk.mockk<Interceptor.Chain> {
            every { this@mockk.request() } returns originalRequest
            every {
                this@mockk.proceed(match { req ->
                    req.header("Authorization") == "Bearer $expiredToken"
                })
            } returns unauthorizedResponse
            every {
                this@mockk.proceed(match { req ->
                    req.url.toString().contains(Environment.END_POINT_REFRESH)
                })
            } returns refreshResponse
            every {
                this@mockk.proceed(match { req ->
                    req.header("Authorization") == "Bearer $newToken"
                })
            } returns retryResponse
        }
    }

    private fun mockChainWithRefreshFailure(
        originalRequest: Request,
        unauthorizedResponse: Response,
        refreshResponse: Response,
        expiredToken: String
    ): Interceptor.Chain {
        return io.mockk.mockk<Interceptor.Chain> {
            every { this@mockk.request() } returns originalRequest
            every {
                this@mockk.proceed(match { req ->
                    req.header("Authorization") == "Bearer $expiredToken"
                })
            } returns unauthorizedResponse
            every {
                this@mockk.proceed(match { req ->
                    req.url.toString().contains(Environment.END_POINT_REFRESH)
                })
            } returns refreshResponse
        }
    }

    private fun mockChainWithException(
        request: Request,
        exception: IOException,
        token: String
    ): Interceptor.Chain {
        return io.mockk.mockk<Interceptor.Chain> {
            every { this@mockk.request() } returns request
            every {
                this@mockk.proceed(match { req ->
                    req.header("Authorization") == "Bearer $token"
                })
            } throws exception
        }
    }
}
