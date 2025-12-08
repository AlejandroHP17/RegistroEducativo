package com.mx.liftechnology.core.util.models

import org.junit.Assert.*
import org.junit.Test

/**
 * Tests unitarios para [ModelResult] y sus implementaciones.
 * 
 * @author Pelkidev
 * @version 1.0.0
 */
class ModelResultTest {

    @Test
    fun `SuccessResult contains correct data`() {
        // Given
        val data = "test_data"
        val result: ModelResult<String, NetworkModelError> = SuccessResult(data)

        // When
        val isSuccess = result is SuccessResult
        val actualData = (result as SuccessResult).data

        // Then
        assertTrue(isSuccess)
        assertEquals(data, actualData)
    }

    @Test
    fun `ErrorResult contains correct error`() {
        // Given
        val error = NetworkModelError.NOT_FOUND
        val result: ModelResult<String, NetworkModelError> = ErrorResult(error)

        // When
        val isError = result is ErrorResult
        val actualError = (result as ErrorResult).error

        // Then
        assertTrue(isError)
        assertEquals(error, actualError)
    }

    @Test
    fun `SuccessResult with LocalModelError`() {
        // Given
        val data = 123
        val result: ModelResult<Int, LocalModelError> = SuccessResult(data)

        // When
        val isSuccess = result is SuccessResult
        val actualData = (result as SuccessResult).data

        // Then
        assertTrue(isSuccess)
        assertEquals(data, actualData)
    }

    @Test
    fun `ErrorResult with LocalModelError`() {
        // Given
        val error = LocalModelError.USER_INCOMPLETE_DATA
        val result: ModelResult<String, LocalModelError> = ErrorResult(error)

        // When
        val isError = result is ErrorResult
        val actualError = (result as ErrorResult).error

        // Then
        assertTrue(isError)
        assertEquals(error, actualError)
    }

    @Test
    fun `ModelResult when expression with SuccessResult`() {
        // Given
        val data = "success"
        val result: ModelResult<String, NetworkModelError> = SuccessResult(data)

        // When
        val message = when (result) {
            is SuccessResult -> "Success: ${result.data}"
            is ErrorResult -> "Error: ${result.error}"
        }

        // Then
        assertEquals("Success: success", message)
    }

    @Test
    fun `ModelResult when expression with ErrorResult`() {
        // Given
        val error = NetworkModelError.UNAUTHORIZED
        val result: ModelResult<String, NetworkModelError> = ErrorResult(error)

        // When
        val message = when (result) {
            is SuccessResult -> "Success: ${result.data}"
            is ErrorResult -> "Error: ${result.error}"
        }

        // Then
        assertEquals("Error: UNAUTHORIZED", message)
    }

    @Test
    fun `SuccessResult equality`() {
        // Given
        val data = "test"
        val result1 = SuccessResult(data)
        val result2 = SuccessResult(data)

        // Then
        assertEquals(result1, result2)
        assertEquals(result1.hashCode(), result2.hashCode())
    }

    @Test
    fun `ErrorResult equality`() {
        // Given
        val error = NetworkModelError.NOT_FOUND
        val result1 = ErrorResult(error)
        val result2 = ErrorResult(error)

        // Then
        assertEquals(result1, result2)
        assertEquals(result1.hashCode(), result2.hashCode())
    }

    @Test
    fun `SuccessResult with different data are not equal`() {
        // Given
        val result1 = SuccessResult("data1")
        val result2 = SuccessResult("data2")

        // Then
        assertNotEquals(result1, result2)
    }

    @Test
    fun `ErrorResult with different errors are not equal`() {
        // Given
        val result1 = ErrorResult(NetworkModelError.NOT_FOUND)
        val result2 = ErrorResult(NetworkModelError.UNAUTHORIZED)

        // Then
        assertNotEquals(result1, result2)
    }
}
