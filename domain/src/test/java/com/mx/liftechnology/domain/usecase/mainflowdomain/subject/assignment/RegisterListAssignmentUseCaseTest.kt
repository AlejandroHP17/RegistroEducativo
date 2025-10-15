package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterListAssignmentRepository
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
 * Tests para [RegisterListAssignmentUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListAssignmentUseCaseTest {

    private lateinit var registerListAssignmentUseCase: RegisterListAssignmentUseCase
    private val registerListAssignmentRepository: RegisterListAssignmentRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        registerListAssignmentUseCase = RegisterListAssignmentUseCaseImp(registerListAssignmentRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de registro de una lista de asignaciones exitoso.
     */
    @Test
    fun `registerListAssignment con respuesta exitosa devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        coEvery { registerListAssignmentRepository.executeRegisterListAssignment(any()) } returns ResultSuccess(listOf("Éxito"))

        // Ejecutamos el caso de uso
        val result = registerListAssignmentUseCase.registerListAssignment()

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }

    /**
     * Test para el caso en que el repositorio devuelve un error.
     */
    @Test
    fun `registerListAssignment con error del repositorio devuelve ErrorState`() = runBlocking {
        // Preparamos el mock
        coEvery { registerListAssignmentRepository.executeRegisterListAssignment(any()) } returns ResultError(FailureService.ServerError)

        // Ejecutamos el caso de uso
        val result = registerListAssignmentUseCase.registerListAssignment()

        // Verificamos el resultado
        assertTrue(result is ErrorState)
    }
}