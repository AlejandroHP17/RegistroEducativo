package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetStudent
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.student.GetStudentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.SuccessState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListStudentAssignmentUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListStudentAssignmentUseCaseTest {

    private lateinit var getListStudentAssignmentUseCase: GetListStudentAssignmentUseCase
    private val getStudentRepository: GetStudentRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        getListStudentAssignmentUseCase = GetListStudentAssignmentUseCase(getStudentRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de obtención de estudiantes para asignación exitoso.
     */
    @Test
    fun `invoke con respuesta exitosa devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        val mockStudents = listOf(ResponseGetStudent("1", "101", "201", "XAXX010101HXXIXXA0", "2001-01-01", "1234567890", "301", "Juan", "Perez", "Gomez"))
        coEvery { getStudentRepository.executeGetListStudent(any()) } returns ResultSuccess(mockStudents)

        // Ejecutamos el caso de uso
        val result = getListStudentAssignmentUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }

    /**
     * Test para el caso en que el repositorio devuelve una lista vacía.
     */
    @Test
    fun `invoke con lista vacia devuelve ErrorUserState`() = runBlocking {
        // Preparamos el mock
        coEvery { getStudentRepository.executeGetListStudent(any()) } returns ResultSuccess(emptyList())

        // Ejecutamos el caso de uso
        val result = getListStudentAssignmentUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorUserState)
    }

    /**
     * Test para el caso en que el repositorio devuelve un error.
     */
    @Test
    fun `invoke con error del repositorio devuelve ErrorUserState`() = runBlocking {
        // Preparamos el mock
        coEvery { getStudentRepository.executeGetListStudent(any()) } returns ResultError(FailureService.BadRequest)

        // Ejecutamos el caso de uso
        val result = getListStudentAssignmentUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorUserState)
    }
}