package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPercentSubjectId
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetPercentSubjectRepository
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
 * Tests para [GetListAssignmentPerSubjectUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListAssignmentPerSubjectUseCaseTest {

    private lateinit var getListAssignmentPerSubjectUseCase: GetListAssignmentPerSubjectUseCase
    private val getPercentSubjectRepository: GetPercentSubjectRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        getListAssignmentPerSubjectUseCase = GetListAssignmentPerSubjectUseCase(getPercentSubjectRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de obtención de asignaciones por materia exitoso.
     */
    @Test
    fun `invoke con respuesta exitosa devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        val mockResponse = listOf(ResponseGetPercentSubjectId(1, 10, 101, "Examen", 201, 1, "Examen"))
        coEvery { getPercentSubjectRepository.executeGetPercentSubject(any()) } returns ResultSuccess(mockResponse)

        // Ejecutamos el caso de uso
        val result = getListAssignmentPerSubjectUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }

    /**
     * Test para el caso en que el repositorio devuelve una lista vacía.
     */
    @Test
    fun `invoke con lista vacia devuelve ErrorState`() = runBlocking {
        // Preparamos el mock
        coEvery { getPercentSubjectRepository.executeGetPercentSubject(any()) } returns ResultSuccess(emptyList())

        // Ejecutamos el caso de uso
        val result = getListAssignmentPerSubjectUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorState)
    }

    /**
     * Test para el caso en que el repositorio devuelve un error.
     */
    @Test
    fun `invoke con error del repositorio devuelve ErrorState`() = runBlocking {
        // Preparamos el mock
        coEvery { getPercentSubjectRepository.executeGetPercentSubject(any()) } returns ResultError(FailureService.ServerError)

        // Ejecutamos el caso de uso
        val result = getListAssignmentPerSubjectUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorState)
    }
}