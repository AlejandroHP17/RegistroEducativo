package com.mx.liftechnology.data.repositoryImpl.auth

import com.mx.liftechnology.core.model.ResponseBasic
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.core.network.api.RequestLogin
import com.mx.liftechnology.core.network.api.RequestRegisterUser
import com.mx.liftechnology.core.network.api.ResponseDataUser
import com.mx.liftechnology.core.network.api.ResponseLogin
import com.mx.liftechnology.core.network.api.ResponseRegisterUser
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

/**
 * Tests para [AuthRepositoryImpl].
 * 
 * Verifica todos los escenarios posibles de las operaciones de autenticación:
 * - Login exitoso y con errores
 * - Registro exitoso y con errores
 * - Obtención de datos de usuario exitosa y con errores
 * - Manejo de excepciones de red
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class AuthRepositoryImplTest {

    private lateinit var authApi: AuthApi
    private lateinit var authRepository: AuthRepositoryImpl

    @Before
    fun setup() {
        authApi = mockk()
        authRepository = AuthRepositoryImpl(authApi)
    }

    // ========== Tests para login ==========

    @Test
    fun `login con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val responseLogin = ResponseLogin(
            accessToken = "access_token_123",
            refreshToken = "refresh_token_456",
            tokenType = "Bearer"
        )
        val responseGeneric = ResponseGeneric(
            data = responseLogin,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        coEvery { authApi.login(any()) } returns response

        // When
        val result = authRepository.login(
            email = "test@example.com",
            password = "password123",
            latitude = 19.4326,
            longitude = -99.1332,
            imei = "IMEI123456"
        )

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals("access_token_123", successResult.data.accessToken)
        assertEquals("refresh_token_456", successResult.data.refreshToken)
        assertEquals("Bearer", successResult.data.tokenType)
    }

    @Test
    fun `login con error 401 retorna ErrorResult UNAUTHORIZED`() = runTest {
        // Given
        val responseBody = "Unauthorized".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<ResponseLogin>>(401, responseBody)

        coEvery { authApi.login(any()) } returns response

        // When
        val result = authRepository.login(
            email = "test@example.com",
            password = "wrong_password",
            latitude = 19.4326,
            longitude = -99.1332,
            imei = "IMEI123456"
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNAUTHORIZED, (result as ErrorResult).error)
    }

    @Test
    fun `login con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        coEvery { authApi.login(any()) } throws ConnectException("No hay conexión")

        // When
        val result = authRepository.login(
            email = "test@example.com",
            password = "password123",
            latitude = 19.4326,
            longitude = -99.1332,
            imei = "IMEI123456"
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }

    @Test
    fun `login con respuesta vacía retorna ErrorResult EMPTY`() = runTest {
        // Given
        val responseGeneric = ResponseGeneric<ResponseLogin>(
            data = null,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        coEvery { authApi.login(any()) } returns response

        // When
        val result = authRepository.login(
            email = "test@example.com",
            password = "password123",
            latitude = 19.4326,
            longitude = -99.1332,
            imei = "IMEI123456"
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `login verifica que se llama con los parámetros correctos`() = runTest {
        // Given
        val responseLogin = ResponseLogin(
            accessToken = "token",
            refreshToken = "refresh",
            tokenType = "Bearer"
        )
        val responseGeneric = ResponseGeneric(
            data = responseLogin,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        val requestSlot = slot<RequestLogin>()
        coEvery { authApi.login(capture(requestSlot)) } returns response

        // When
        authRepository.login(
            email = "test@example.com",
            password = "password123",
            latitude = 19.4326,
            longitude = -99.1332,
            imei = "IMEI123456"
        )

        // Then
        assertNotNull(requestSlot.captured)
        assertEquals("test@example.com", requestSlot.captured.email)
        assertEquals("password123", requestSlot.captured.password)
        assertEquals(19.4326, requestSlot.captured.latitude, 0.001)
        assertEquals(-99.1332, requestSlot.captured.longitude, 0.001)
        assertEquals("IMEI123456", requestSlot.captured.imei)
    }

    // ========== Tests para register ==========

    @Test
    fun `register con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val responseRegister = ResponseRegisterUser(
            email = "test@example.com",
            firstName = "Juan",
            lastName = "Pérez",
            phone = "55555555",
            accessLevel = 2,
            accessCode = 1,
            isActive = true,
            id = 1,
            createdAt = "2024-01-01"
        )
        val responseGeneric = ResponseGeneric(
            data = responseRegister,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        coEvery { authApi.register(any()) } returns response

        // When
        val result = authRepository.register(
            email = "test@example.com",
            pass = "password123",
            activationCode = "CODE123"
        )

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals("test@example.com", successResult.data.email)
        assertEquals("Juan", successResult.data.firstName)
        assertEquals("Pérez", successResult.data.lastName)
        assertEquals(2, successResult.data.accessLevel)
        assertEquals(1, successResult.data.userId)
    }

    @Test
    fun `register con email en mayúsculas lo convierte a minúsculas`() = runTest {
        // Given
        val responseRegister = ResponseRegisterUser(
            email = "test@example.com",
            firstName = "Juan",
            lastName = "Pérez",
            phone="55555555",
            accessLevel = 2,
            accessCode = 1,
            isActive = true,
            id = 1,
            createdAt = "2024-01-01"
        )
        val responseGeneric = ResponseGeneric(
            data = responseRegister,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        val requestSlot = slot<RequestRegisterUser>()
        coEvery { authApi.register(capture(requestSlot)) } returns response

        // When
        authRepository.register(
            email = "TEST@EXAMPLE.COM",
            pass = "password123",
            activationCode = "CODE123"
        )

        // Then
        assertNotNull(requestSlot.captured)
        assertEquals("test@example.com", requestSlot.captured.email)
    }

    @Test
    fun `register con error 409 retorna ErrorResult CONFLICT`() = runTest {
        // Given
        val responseBody = "Conflict".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<ResponseRegisterUser>>(409, responseBody)

        coEvery { authApi.register(any()) } returns response

        // When
        val result = authRepository.register(
            email = "existing@example.com",
            pass = "password123",
            activationCode = "CODE123"
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.CONFLICT, (result as ErrorResult).error)
    }

    @Test
    fun `register con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        coEvery { authApi.register(any()) } throws ConnectException("No hay conexión")

        // When
        val result = authRepository.register(
            email = "test@example.com",
            pass = "password123",
            activationCode = "CODE123"
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }

    // ========== Tests para getData ==========

    @Test
    fun `getData con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val responseDataUser = ResponseDataUser(
            email = "test@example.com",
            name = "Juan",
            lastName = "Pérez",
            phone = "1234567890",
            accessLevelId = 2,
            accessCodeId = 1,
            isActive = true,
            userId = 1,
            createdAt = ""
        )
        val responseGeneric = ResponseGeneric(
            data = responseDataUser,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        coEvery { authApi.getUserData() } returns response

        // When
        val result = authRepository.getData()

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals("test@example.com", successResult.data.email)
        assertEquals("Juan", successResult.data.name)
        assertEquals("Pérez", successResult.data.lastName)
        assertEquals("1234567890", successResult.data.phone)
        assertEquals(2, successResult.data.accessLevelId)
        assertEquals(1, successResult.data.userId)
        assertEquals(true, successResult.data.isActive)
    }

    @Test
    fun `getData con error 401 retorna ErrorResult UNAUTHORIZED`() = runTest {
        // Given
        val responseBody = "Unauthorized".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<ResponseDataUser>>(401, responseBody)

        coEvery { authApi.getUserData() } returns response

        // When
        val result = authRepository.getData()

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNAUTHORIZED, (result as ErrorResult).error)
    }

    @Test
    fun `getData con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        coEvery { authApi.getUserData() } throws ConnectException("No hay conexión")

        // When
        val result = authRepository.getData()

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }

    @Test
    fun `getData con respuesta vacía retorna ErrorResult EMPTY`() = runTest {
        // Given
        val responseGeneric = ResponseGeneric<ResponseDataUser>(
            data = null,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        coEvery { authApi.getUserData() } returns response

        // When
        val result = authRepository.getData()

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.EMPTY, (result as ErrorResult).error)
    }
}
