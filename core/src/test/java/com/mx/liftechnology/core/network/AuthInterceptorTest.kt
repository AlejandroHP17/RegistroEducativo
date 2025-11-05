package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.network.environment.Environment
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Tests para [AuthInterceptor].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class AuthInterceptorTest {

    private lateinit var tokenProvider: TokenProvider
    private lateinit var authInterceptor: AuthInterceptor
    private lateinit var chain: Interceptor.Chain

    @Before
    fun setUp() {
        tokenProvider = mockk()
        authInterceptor = AuthInterceptor(tokenProvider)
        chain = mockk()

        mockkObject(Environment)
        every { Environment.URL_BASE } returns "http://test.com/api/v1/"
        every { Environment.END_POINT_LOGIN } returns "auth/login"
        every { Environment.END_POINT_REGISTER } returns "register/teacherRegister"
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `intercept añade token de autorización para endpoints que requieren autenticación`() {
        val token = "test_token"
        val request = Request.Builder()
            .url("http://test.com/api/v1/test")
            .build()
        val response = mockk<Response>()

        every { tokenProvider.getToken() } returns token
        every { chain.request() } returns request
        every { chain.proceed(any()) } returns response

        val result = authInterceptor.intercept(chain)

        verify { tokenProvider.getToken() }
        verify { chain.proceed(any()) }
        assertEquals(response, result)
    }

    @Test
    fun `intercept no añade token para endpoint de login`() {
        val request = Request.Builder()
            .url("http://test.com/api/v1/auth/login")
            .build()
        val response = mockk<Response>()

        every { chain.request() } returns request
        every { chain.proceed(any()) } returns response

        val result = authInterceptor.intercept(chain)

        verify(exactly = 0) { tokenProvider.getToken() }
        verify { chain.proceed(request) }
        assertEquals(response, result)
    }

    @Test
    fun `intercept no añade token para endpoint de registro`() {
        val request = Request.Builder()
            .url("http://test.com/api/v1/register/teacherRegister")
            .build()
        val response = mockk<Response>()

        every { chain.request() } returns request
        every { chain.proceed(any()) } returns response

        val result = authInterceptor.intercept(chain)

        verify(exactly = 0) { tokenProvider.getToken() }
        verify { chain.proceed(request) }
        assertEquals(response, result)
    }

    @Test
    fun `intercept usa token vacío si no hay token disponible`() {
        val request = Request.Builder()
            .url("http://test.com/api/v1/test")
            .build()
        val response = mockk<Response>()

        every { tokenProvider.getToken() } returns null
        every { chain.request() } returns request
        every { chain.proceed(any()) } returns response

        val result = authInterceptor.intercept(chain)

        verify { tokenProvider.getToken() }
        verify { chain.proceed(any()) }
        assertEquals(response, result)
    }
}

