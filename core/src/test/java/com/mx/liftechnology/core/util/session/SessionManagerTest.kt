package com.mx.liftechnology.core.util.session

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests unitarios para [SessionManager].
 * 
 * @author Pelkidev
 * @version 1.0.0
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SessionManagerTest {

    private lateinit var sessionManager: SessionManager
    private lateinit var testScope: TestScope

    @Before
    fun setup() {
        sessionManager = SessionManager()
        testScope = TestScope(StandardTestDispatcher())
    }

    @Test
    fun `notifySessionExpired emits true in sessionExpired flow`() = runTest {
        // Given
        val collectedValues = mutableListOf<Boolean>()

        // When
        val job = launch {
            sessionManager.sessionExpired.collect { collectedValues.add(it) }
        }
        
        // Esperar un poco para que el collector esté listo
        kotlinx.coroutines.delay(10)
        sessionManager.notifySessionExpired()
        kotlinx.coroutines.delay(10)
        job.cancel()

        // Then
        assertTrue(collectedValues.isNotEmpty())
        assertTrue(collectedValues.last())
    }

    @Test
    fun `resetSessionExpired emits false in sessionExpired flow`() = runTest {
        // Given
        val collectedValues = mutableListOf<Boolean>()

        // When
        val job = launch {
            sessionManager.sessionExpired.collect { collectedValues.add(it) }
        }
        
        // Esperar un poco para que el collector esté listo
        kotlinx.coroutines.delay(10)
        sessionManager.resetSessionExpired()
        kotlinx.coroutines.delay(10)
        job.cancel()

        // Then
        assertTrue(collectedValues.isNotEmpty())
        assertFalse(collectedValues.last())
    }

    @Test
    fun `sessionExpired flow emits multiple values correctly`() = runTest {
        // Given
        val collectedValues = mutableListOf<Boolean>()

        // When
        val job = launch {
            sessionManager.sessionExpired.collect { collectedValues.add(it) }
        }
        
        // Esperar un poco para que el collector esté listo
        kotlinx.coroutines.delay(10)
        sessionManager.notifySessionExpired()
        kotlinx.coroutines.delay(10)
        sessionManager.resetSessionExpired()
        kotlinx.coroutines.delay(10)
        sessionManager.notifySessionExpired()
        kotlinx.coroutines.delay(10)
        job.cancel()

        // Then
        assertTrue(collectedValues.size >= 3)
        assertTrue(collectedValues.contains(true))
        assertTrue(collectedValues.contains(false))
    }

    @Test
    fun `sessionExpired is a SharedFlow that can have multiple collectors`() = runTest {
        // Given
        val collectedValues1 = mutableListOf<Boolean>()
        val collectedValues2 = mutableListOf<Boolean>()

        // When
        val job1 = launch {
            sessionManager.sessionExpired.collect { collectedValues1.add(it) }
        }
        val job2 = launch {
            sessionManager.sessionExpired.collect { collectedValues2.add(it) }
        }
        
        // Esperar un poco para que los collectors estén listos
        kotlinx.coroutines.delay(10)
        sessionManager.notifySessionExpired()
        kotlinx.coroutines.delay(10)
        job1.cancel()
        job2.cancel()

        // Then
        assertTrue(collectedValues1.isNotEmpty())
        assertTrue(collectedValues2.isNotEmpty())
        assertTrue(collectedValues1.last())
        assertTrue(collectedValues2.last())
    }
}
