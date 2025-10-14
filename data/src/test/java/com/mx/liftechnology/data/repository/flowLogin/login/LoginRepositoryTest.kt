package com.mx.liftechnology.data.repository.flowLogin.login

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowLogin.LoginApiCall
import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestLogin
import com.mx.liftechnology.core.network.apiCall.flowLogin.ResponseLogin
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Tests para [LoginRepository].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginRepositoryTest {

    private lateinit var loginRepository: LoginRepository
    private val loginApiCall: LoginApiCall = mockk()

    @Before
    fun setUp() {
        loginRepository = LoginRepositoryImp(loginApiCall)
    }

    @Test
    fun `executeLogin con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockResponseLogin: ResponseLogin = mockk()
        val mockBody: ResponseGeneric<ResponseLogin>? = ResponseGeneric(mockResponseLogin, mockk())
        val mockResponse: Response<ResponseGeneric<ResponseLogin>?> = Response.success(mockBody)
        
        coEvery { loginApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = loginRepository.executeLogin(RequestLogin("", "", "", "", ""))

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
        assertEquals(mockResponseLogin, (result as ResultSuccess).data)
    }

    @Test
    fun `executeLogin con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<ResponseLogin>?> = Response.error(400, mockResponseBody)

        coEvery { loginApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = loginRepository.executeLogin(RequestLogin("", "", "", "", ""))

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}