package com.mx.liftechnology.data.repository.flowLogin.register

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowLogin.RegisterUserApiCall
import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestRegisterUser
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Tests para [RegisterUserRepository].
 * Esta clase contiene los tests unitarios para el repositorio de registro de usuarios, 
 * verificando que la lógica de la capa de datos funcione como se espera.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserRepositoryTest {

    private lateinit var registerUserRepository: RegisterUserRepository
    private val registerUserApiCall: RegisterUserApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [RegisterUserRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        registerUserRepository = RegisterUserRepositoryImp(registerUserApiCall)
    }

    /**
     * Test para verificar el caso de éxito del registro de usuario.
     */
    @Test
    fun `executeRegisterUser con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<String>?> = ResponseGeneric(listOf("Registro exitoso"), mockk())
        val mockResponse: Response<ResponseGeneric<List<String>?>?> = Response.success(mockBody)
        
        coEvery { registerUserApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = registerUserRepository.executeRegisterUser(RequestRegisterUser("", "", ""))

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
    }

    /**
     * Test para verificar el caso de error del registro de usuario.
     */
    @Test
    fun `executeRegisterUser con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<List<String>?>?> = Response.error(400, mockResponseBody)
        
        coEvery { registerUserApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = registerUserRepository.executeRegisterUser(RequestRegisterUser("", "", ""))

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}