package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetListAssignmentRepository
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
 * Tests para [GetListAssignmentUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListAssignmentUseCaseTest {

    private lateinit var getListAssignmentUseCase: GetListAssignmentUseCase
    private val getListAssignmentRepository: GetListAssignmentRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        getListAssignmentUseCase = GetListAssignmentUseCaseImp(getListAssignmentRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de obtención de asignaciones exitoso.
     */
    @Test
    fun `getListAssignment con respuesta exitosa devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        coEvery { getListAssignmentRepository.executeGetListAssignment(any()) } returns ResultSuccess(listOf("Asignación 1"))

        // Ejecutamos el caso de uso
        val result = getListAssignmentUseCase.getListAssignment()

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }

    /**
     * Test para el caso en que el repositorio devuelve una lista vacía.
     */
    @Test
    fun `getListAssignment con lista vacia devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        coEvery { getListAssignmentRepository.executeGetListAssignment(any()) } returns ResultSuccess(emptyList())

        // Ejecutamos el caso de uso
        val result = getListAssignmentUseCase.getListAssignment()

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }

    /**
     * Test para el caso en que el repositorio devuelve un error.
     */
    @Test
    fun `getListAssignment con error del repositorio devuelve ErrorState`() = runBlocking {
        // Preparamos el mock
        coEvery { getListAssignmentRepository.executeGetListAssignment(any()) } returns ResultError(FailureService.ServerError)

        // Ejecutamos el caso de uso
        val result = getListAssignmentUseCase.getListAssignment()

        // Verificamos el resultado
        assertTrue(result is ErrorState)
    }
}