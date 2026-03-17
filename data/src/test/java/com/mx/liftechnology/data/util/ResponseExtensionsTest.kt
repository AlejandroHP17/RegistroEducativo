package com.mx.liftechnology.data.util

import com.mx.liftechnology.core.model.ResponseBasic
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

/**
 * Tests para las funciones de extensión de [ResponseExtensions].
 * 
 * Verifica todos los escenarios posibles de las funciones executeOrError, executeOrErrorDirect,
 * safeApiCall y safeApiCallDirect.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ResponseExtensionsTest {

    // ========== Tests para executeOrError ==========

    @Test
    fun `executeOrError con respuesta exitosa y datos válidos retorna SuccessResult`() = runTest {
        // Given
        val data = "Test Data"
        val responseGeneric = ResponseGeneric(
            data = data,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)
        val mapper: (String) -> String = { it.uppercase() }

        // When
        val result = response.executeOrError(mapper)

        // Then
        assertTrue(result is SuccessResult)
        assertEquals("TEST DATA", (result as SuccessResult).data)
    }

    @Test
    fun `executeOrError con respuesta exitosa pero data null retorna ErrorResult EMPTY`() = runTest {
        // Given
        val responseGeneric = ResponseGeneric<String>(
            data = null,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)
        val mapper: (String) -> String = { it }

        // When
        val result = response.executeOrError(mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `executeOrError con respuesta exitosa pero body null retorna ErrorResult EMPTY`() = runTest {
        // Given
        val response = Response.success<ResponseGeneric<String>>(null)
        val mapper: (String) -> String = { it }

        // When
        val result = response.executeOrError(mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `executeOrError con mapper que retorna null retorna ErrorResult EMPTY`() = runTest {
        // Given
        val data = "Test Data"
        val responseGeneric = ResponseGeneric(
            data = data,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)
        val mapper: (String) -> String? = { null }

        // When
        val result = response.executeOrError(mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `executeOrError con respuesta no exitosa retorna ErrorResult con error HTTP`() = runTest {
        // Given
        val responseBody = "Error".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<String>>(400, responseBody)
        val mapper: (String) -> String = { it }

        // When
        val result = response.executeOrError(mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.BAD_REQUEST, (result as ErrorResult).error)
    }

    @Test
    fun `executeOrError con excepción durante el mapeo retorna ErrorResult`() = runTest {
        // Given
        val data = "Test Data"
        val responseGeneric = ResponseGeneric(
            data = data,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)
        val mapper: (String) -> String = { throw RuntimeException("Error en mapper") }

        // When
        val result = response.executeOrError(mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNKNOWN, (result as ErrorResult).error)
    }

    // ========== Tests para executeOrErrorDirect ==========

    @Test
    fun `executeOrErrorDirect con respuesta exitosa y datos válidos retorna SuccessResult`() = runTest {
        // Given
        val data = "Test Data"
        val response = Response.success(data)
        val mapper: (String) -> String = { it.uppercase() }

        // When
        val result = response.executeOrErrorDirect(mapper)

        // Then
        assertTrue(result is SuccessResult)
        assertEquals("TEST DATA", (result as SuccessResult).data)
    }

    @Test
    fun `executeOrErrorDirect con respuesta exitosa pero body null retorna ErrorResult EMPTY`() = runTest {
        // Given
        val response = Response.success<String>(null)
        val mapper: (String) -> String = { it }

        // When
        val result = response.executeOrErrorDirect(mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `executeOrErrorDirect con mapper que retorna null retorna ErrorResult EMPTY`() = runTest {
        // Given
        val data = "Test Data"
        val response = Response.success(data)
        val mapper: (String) -> String? = { null }

        // When
        val result = response.executeOrErrorDirect(mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `executeOrErrorDirect con respuesta no exitosa retorna ErrorResult con error HTTP`() = runTest {
        // Given
        val responseBody = "Error".toResponseBody("application/json".toMediaType())
        val response = Response.error<String>(401, responseBody)
        val mapper: (String) -> String = { it }

        // When
        val result = response.executeOrErrorDirect(mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNAUTHORIZED, (result as ErrorResult).error)
    }

    @Test
    fun `executeOrErrorDirect con excepción durante el mapeo retorna ErrorResult`() = runTest {
        // Given
        val data = "Test Data"
        val response = Response.success(data)
        val mapper: (String) -> String = { throw IllegalArgumentException("Error en mapper") }

        // When
        val result = response.executeOrErrorDirect(mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNKNOWN, (result as ErrorResult).error)
    }

    // ========== Tests para safeApiCall ==========

    @Test
    fun `safeApiCall con llamada exitosa retorna SuccessResult`() = runTest {
        // Given
        val data = "Test Data"
        val responseGeneric = ResponseGeneric(
            data = data,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)
        val apiCall: suspend () -> Response<ResponseGeneric<String>> = { response }
        val mapper: (String) -> String = { it.uppercase() }

        // When
        val result = safeApiCall(apiCall, mapper)

        // Then
        assertTrue(result is SuccessResult)
        assertEquals("TEST DATA", (result as SuccessResult).data)
    }

    @Test
    fun `safeApiCall con excepción de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        val apiCall: suspend () -> Response<ResponseGeneric<String>> = { 
            throw ConnectException("No hay conexión") 
        }
        val mapper: (String) -> String = { it }

        // When
        val result = safeApiCall(apiCall, mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }

    @Test
    fun `safeApiCall con excepción genérica retorna ErrorResult UNKNOWN`() = runTest {
        // Given
        val apiCall: suspend () -> Response<ResponseGeneric<String>> = { 
            throw RuntimeException("Error genérico") 
        }
        val mapper: (String) -> String = { it }

        // When
        val result = safeApiCall(apiCall, mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNKNOWN, (result as ErrorResult).error)
    }

    @Test
    fun `safeApiCall con respuesta no exitosa retorna ErrorResult`() = runTest {
        // Given
        val responseBody = "Error".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<String>>(404, responseBody)
        val apiCall: suspend () -> Response<ResponseGeneric<String>> = { response }
        val mapper: (String) -> String = { it }

        // When
        val result = safeApiCall(apiCall, mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }

    // ========== Tests para safeApiCallDirect ==========

    @Test
    fun `safeApiCallDirect con llamada exitosa retorna SuccessResult`() = runTest {
        // Given
        val data = "Test Data"
        val response = Response.success(data)
        val apiCall: suspend () -> Response<String> = { response }
        val mapper: (String) -> String = { it.uppercase() }

        // When
        val result = safeApiCallDirect(apiCall, mapper)

        // Then
        assertTrue(result is SuccessResult)
        assertEquals("TEST DATA", (result as SuccessResult).data)
    }

    @Test
    fun `safeApiCallDirect con excepción de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        val apiCall: suspend () -> Response<String> = { 
            throw ConnectException("No hay conexión") 
        }
        val mapper: (String) -> String = { it }

        // When
        val result = safeApiCallDirect(apiCall, mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }

    @Test
    fun `safeApiCallDirect con excepción genérica retorna ErrorResult UNKNOWN`() = runTest {
        // Given
        val apiCall: suspend () -> Response<String> = { 
            throw RuntimeException("Error genérico") 
        }
        val mapper: (String) -> String = { it }

        // When
        val result = safeApiCallDirect(apiCall, mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNKNOWN, (result as ErrorResult).error)
    }

    @Test
    fun `safeApiCallDirect con respuesta no exitosa retorna ErrorResult`() = runTest {
        // Given
        val responseBody = "Error".toResponseBody("application/json".toMediaType())
        val response = Response.error<String>(500, responseBody)
        val apiCall: suspend () -> Response<String> = { response }
        val mapper: (String) -> String = { it }

        // When
        val result = safeApiCallDirect(apiCall, mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.SERVER_ERROR, (result as ErrorResult).error)
    }

    @Test
    fun `safeApiCallDirect con timeout retorna ErrorResult TIMEOUT`() = runTest {
        // Given
        val apiCall: suspend () -> Response<String> = { 
            throw java.net.SocketTimeoutException("Timeout") 
        }
        val mapper: (String) -> String = { it }

        // When
        val result = safeApiCallDirect(apiCall, mapper)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.TIMEOUT, (result as ErrorResult).error)
    }
}
