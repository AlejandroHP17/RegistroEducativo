package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseStudentJobs
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterAssignmentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.SuccessState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterAssignmentUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterAssignmentUseCaseTest {

    private lateinit var registerAssignmentUseCase: RegisterAssignmentUseCase
    private val registerAssignmentRepository: RegisterAssignmentRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        registerAssignmentUseCase = RegisterAssignmentUseCase(registerAssignmentRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de registro de asignación exitoso.
     */
    @Test
    fun `invoke con datos validos debe devolver SuccessState`() = runBlocking {
        // Preparamos el mock
        val mockResponse = listOf(ResponseStudentJobs("2024-01-01", 1, "Éxito"))
        coEvery { registerAssignmentRepository.RegisterAssignment(any()) } returns ResultSuccess(mockResponse)

        // Ejecutamos el caso de uso
        val result = registerAssignmentUseCase.invoke("Test Job", 1, "2024-01-01", emptyList())

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }

    /**
     * Test para el caso en que el repositorio devuelve un error.
     */
    @Test
    fun `invoke con error del repositorio debe devolver ErrorState`() = runBlocking {
        // Preparamos el mock
        coEvery { registerAssignmentRepository.RegisterAssignment(any()) } returns ResultError(FailureService.ServerError)

        // Ejecutamos el caso de uso
        val result = registerAssignmentUseCase.invoke("Test Job", 1, "2024-01-01", emptyList())

        // Verificamos el resultado
        assertTrue(result is ErrorState)
    }
}