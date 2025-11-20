package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import com.mx.liftechnology.core.network.apiCall.schoolCycle.ResponseCctSchool
import com.mx.liftechnology.data.repository.schoolCycle.school.GetCctRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.usecase.schoolCycle.school.GetCctUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetCctUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetCctUseCaseTest {

    private lateinit var getCctUseCase: GetCctUseCase
    private val getCctRepository: GetCctRepository = mockk()

    @Before
    fun setUp() {
        getCctUseCase = GetCctUseCase(getCctRepository)
    }

    /**
     * Test para el flujo exitoso donde se obtiene la información de la escuela
     * y se generan correctamente los datos para los spinners.
     */
    @Test
    fun `invoke con CCT valido devuelve SuccessState con datos de spinner correctos`() = runBlocking {
        // Preparamos el mock de la respuesta del repositorio
        val mockResponse = ResponseCctSchool(
            cct = "1234567890",
            schoolId = 1,
            schoolName = "Escuela Primaria Test",
            schoolCycleType = "Anual",
            schoolCycleTypeId = 1,
            schoolType = "Primaria",
            shift = "Matutino"
        )
        coEvery { getCctRepository.executeGetCct(any()) } returns ResultSuccess(mockResponse)

        // Ejecutamos el caso de uso
        val result = getCctUseCase.invoke("1234567890")

        // Verificamos que el resultado sea exitoso
        assertTrue(result is SuccessResult)
        val successData = (result as SuccessResult).result

        // Verificamos los datos de la escuela
        assertEquals("Escuela Primaria Test", successData?.result?.schoolName)

        // Verificamos la lógica de los spinners
        assertEquals(listOf("1"), successData?.spinners?.cycle) // Anual -> 1 ciclo
        assertEquals(listOf("1", "2", "3", "4", "5", "6"), successData?.spinners?.grade) // Primaria -> 6 grados
        assertEquals(listOf("A", "B", "C", "D"), successData?.spinners?.group) // Primaria -> 4 grupos
    }

    /**
     * Test para el caso en que el CCT no se encuentra (error 404).
     */
    @Test
    fun `invoke con CCT no encontrado devuelve ErrorUserState`() = runBlocking {
        // Preparamos el mock para un error de "No Encontrado"
        coEvery { getCctRepository.executeGetCct(any()) } returns ResultError(FailureService.NotFound)

        // Ejecutamos el caso de uso
        val result = getCctUseCase.invoke("0000000000")

        // Verificamos que se devuelve el estado de error de usuario correcto
        assertTrue(result is ErrorUserResult)
    }

    /**
     * Test para el caso en que el repositorio lanza una excepción inesperada.
     */
    @Test
    fun `invoke cuando el repositorio lanza excepcion devuelve ErrorState`() = runBlocking {
        // Preparamos el mock para que lance una excepción
        coEvery { getCctRepository.executeGetCct(any()) } throws RuntimeException("ModelError de red")

        // Ejecutamos el caso de uso
        val result = getCctUseCase.invoke("1234567890")

        // Verificamos que se devuelve un estado de error genérico
        assertTrue(result is ErrorResult)
    }
}