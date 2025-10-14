package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListSubject
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.GetListSubjectRepository
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.SuccessState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListSubjectUseCase].
 * Esta clase contiene los tests unitarios para el caso de uso que obtiene la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListSubjectUseCaseTest {

    private lateinit var getListSubjectUseCase: GetListSubjectUseCase
    private val getListSubjectRepository: GetListSubjectRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [GetListSubjectUseCase] y sus dependencias.
     */
    @Before
    fun setUp() {
        getListSubjectUseCase = GetListSubjectUseCase(getListSubjectRepository, preferenceUseCase)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de la lista de materias.
     */
    @Test
    fun `invoke con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockSubject = ResponseGetListSubject(1, "Matemáticas")
        coEvery { getListSubjectRepository.executeGetListSubject(any()) } returns ResultSuccess(listOf(mockSubject))

        // Ejecutamos el método a probar
        val result = getListSubjectUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }
}