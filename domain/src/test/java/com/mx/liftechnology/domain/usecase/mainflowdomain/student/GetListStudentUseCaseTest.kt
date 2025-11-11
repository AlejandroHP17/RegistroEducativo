package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.network.apiCall.student.ResponseGetStudent
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.student.GetStudentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.usecase.student.GetListStudentUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [com.mx.liftechnology.domain.usecase.student.GetListStudentUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListStudentUseCaseTest {

    private lateinit var getListStudentUseCase: GetListStudentUseCase
    private val getStudentRepository: GetStudentRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        getListStudentUseCase = GetListStudentUseCase(getStudentRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de obtención de estudiantes exitoso.
     */
    @Test
    fun `invoke con respuesta exitosa devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        val mockStudents = listOf(ResponseGetStudent("1", "101", "201", "XAXX010101HXXIXXA0", "2001-01-01", "1234567890", "301", "Juan", "Perez", "Gomez"))
        coEvery { getStudentRepository.executeGetListStudent(any()) } returns ResultSuccess(mockStudents)

        // Ejecutamos el caso de uso
        val result = getListStudentUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is SuccessResult)
    }

    /**
     * Test para el caso en que el repositorio devuelve una lista vacía.
     */
    @Test
    fun `invoke con lista vacia devuelve ErrorUserState`() = runBlocking {
        // Preparamos el mock
        coEvery { getStudentRepository.executeGetListStudent(any()) } returns ResultSuccess(emptyList())

        // Ejecutamos el caso de uso
        val result = getListStudentUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorUserResult)
    }

    /**
     * Test para el caso en que el repositorio devuelve un error.
     */
    @Test
    fun `invoke con error del repositorio devuelve ErrorUserState`() = runBlocking {
        // Preparamos el mock
        coEvery { getStudentRepository.executeGetListStudent(any()) } returns ResultError(FailureService.BadRequest)

        // Ejecutamos el caso de uso
        val result = getListStudentUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorUserResult)
    }
}