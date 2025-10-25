package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.evaluationType

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.evaluationtype.GetListEvaluationTypeRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.SuccessResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListEvaluationTypeUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListEvaluationTypetUseCaseTest {

    private lateinit var getListEvaluationTypeUseCase: GetListEvaluationTypeUseCase
    private val getListEvaluationTypeRepository: GetListEvaluationTypeRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        getListEvaluationTypeUseCase = GetListEvaluationTypeUseCaseImp(getListEvaluationTypeRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de obtención de tipos de evaluación exitoso.
     */
    @Test
    fun `getListEvaluationType con respuesta exitosa devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        coEvery { getListEvaluationTypeRepository.executeGetListEvaluationType(any()) } returns ResultSuccess(listOf("Examen", "Proyecto"))

        // Ejecutamos el caso de uso
        val result = getListEvaluationTypeUseCase.getListEvaluationType()

        // Verificamos el resultado
        assertTrue(result is SuccessResult)
    }

    /**
     * Test para el caso en que el repositorio devuelve una lista vacía.
     */
    @Test
    fun `getListEvaluationType con lista vacia devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        coEvery { getListEvaluationTypeRepository.executeGetListEvaluationType(any()) } returns ResultSuccess(emptyList())

        // Ejecutamos el caso de uso
        val result = getListEvaluationTypeUseCase.getListEvaluationType()

        // Verificamos el resultado
        assertTrue(result is SuccessResult) // Una lista vacía es un resultado válido.
    }

    /**
     * Test para el caso en que el repositorio devuelve un error.
     */
    @Test
    fun `getListEvaluationType con error del repositorio devuelve ErrorState`() = runBlocking {
        // Preparamos el mock
        coEvery { getListEvaluationTypeRepository.executeGetListEvaluationType(any()) } returns ResultError(FailureService.ServerError)

        // Ejecutamos el caso de uso
        val result = getListEvaluationTypeUseCase.getListEvaluationType()

        // Verificamos el resultado
        assertTrue(result is ErrorResult)
    }
}