package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assessment

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assessment.GetAssessmentTypeRepository
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
 * Tests para [GetListAssessmentTypeUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListAssessmentTypeUseCaseTest {

    private lateinit var getListAssessmentTypeUseCase: GetListAssessmentTypeUseCase
    private val getAssessmentTypeRepository: GetAssessmentTypeRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        getListAssessmentTypeUseCase = GetListAssessmentTypeUseCase(getAssessmentTypeRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de obtención de tipos de evaluación exitoso.
     */
    @Test
    fun `invoke con respuesta exitosa devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        val mockResponse = listOf(ResponseGetListAssessmentType(1, "Examen", 101))
        coEvery { getAssessmentTypeRepository.executeGetListAssessment(any()) } returns ResultSuccess(mockResponse)

        // Ejecutamos el caso de uso
        val result = getListAssessmentTypeUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }

    /**
     * Test para el caso en que el repositorio devuelve una lista vacía.
     */
    @Test
    fun `invoke con lista vacia no devuelve error`() = runBlocking {
        // Preparamos el mock
        coEvery { getAssessmentTypeRepository.executeGetListAssessment(any()) } returns ResultSuccess(emptyList())

        // Ejecutamos el caso de uso
        val result = getListAssessmentTypeUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is SuccessState) // A diferencia de otros casos de uso, una lista vacía aquí es un estado válido.
    }

    /**
     * Test para el caso en que el repositorio devuelve un error.
     */
    @Test
    fun `invoke con error del repositorio devuelve ErrorState`() = runBlocking {
        // Preparamos el mock
        coEvery { getAssessmentTypeRepository.executeGetListAssessment(any()) } returns ResultError(FailureService.ServerError)

        // Ejecutamos el caso de uso
        val result = getListAssessmentTypeUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorState)
    }
}