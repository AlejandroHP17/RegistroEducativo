package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetStudent
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.student.GetStudentRepository
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.SuccessState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListStudentUseCase].
 * Esta clase contiene los tests unitarios para el caso de uso que obtiene la lista de estudiantes.
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
     * Se ejecuta antes de cada test para inicializar el [GetListStudentUseCase] y sus dependencias.
     */
    @Before
    fun setUp() {
        getListStudentUseCase = GetListStudentUseCase(getStudentRepository, preferenceUseCase)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de la lista de estudiantes.
     */
    @Test
    fun `invoke con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockStudent = ResponseGetStudent("1", "1", "1", "XAXX010101HXXIXXA0", "2001-01-01", "1234567890", "1", "Juan", "Perez", "Gomez")
        coEvery { getStudentRepository.executeGetListStudent(any()) } returns ResultSuccess(listOf(mockStudent))

        // Ejecutamos el método a probar
        val result = getListStudentUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }
}