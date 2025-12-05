package com.mx.liftechnology.core.network.util

import org.junit.Assert.*
import org.junit.Test

/**
 * Tests unitarios para [NetworkConfig].
 * 
 * @author Pelkidev
 * @version 1.0.0
 */
class NetworkConfigTest {

    @Test
    fun `CONNECT_TIMEOUT_SECONDS is 15 seconds`() {
        // When
        val timeout = NetworkConfig.CONNECT_TIMEOUT_SECONDS

        // Then
        assertEquals(15L, timeout)
    }

    @Test
    fun `READ_TIMEOUT_SECONDS is 15 seconds`() {
        // When
        val timeout = NetworkConfig.READ_TIMEOUT_SECONDS

        // Then
        assertEquals(15L, timeout)
    }

    @Test
    fun `WRITE_TIMEOUT_SECONDS is 15 seconds`() {
        // When
        val timeout = NetworkConfig.WRITE_TIMEOUT_SECONDS

        // Then
        assertEquals(15L, timeout)
    }

    @Test
    fun `all timeouts are equal`() {
        // When
        val connectTimeout = NetworkConfig.CONNECT_TIMEOUT_SECONDS
        val readTimeout = NetworkConfig.READ_TIMEOUT_SECONDS
        val writeTimeout = NetworkConfig.WRITE_TIMEOUT_SECONDS

        // Then
        assertEquals(connectTimeout, readTimeout)
        assertEquals(readTimeout, writeTimeout)
        assertEquals(connectTimeout, writeTimeout)
    }

    @Test
    fun `timeouts are positive values`() {
        // When
        val connectTimeout = NetworkConfig.CONNECT_TIMEOUT_SECONDS
        val readTimeout = NetworkConfig.READ_TIMEOUT_SECONDS
        val writeTimeout = NetworkConfig.WRITE_TIMEOUT_SECONDS

        // Then
        assertTrue(connectTimeout > 0)
        assertTrue(readTimeout > 0)
        assertTrue(writeTimeout > 0)
    }

    @Test
    fun `timeouts are reasonable values`() {
        // When
        val connectTimeout = NetworkConfig.CONNECT_TIMEOUT_SECONDS
        val readTimeout = NetworkConfig.READ_TIMEOUT_SECONDS
        val writeTimeout = NetworkConfig.WRITE_TIMEOUT_SECONDS

        // Then
        // Los timeouts deben ser razonables (entre 5 y 60 segundos)
        assertTrue(connectTimeout >= 5L && connectTimeout <= 60L)
        assertTrue(readTimeout >= 5L && readTimeout <= 60L)
        assertTrue(writeTimeout >= 5L && writeTimeout <= 60L)
    }
}
