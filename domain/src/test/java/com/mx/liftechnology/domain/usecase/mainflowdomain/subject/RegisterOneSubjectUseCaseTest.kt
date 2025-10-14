package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.RegisterSubjectRepository
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.SuccessState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterOneSubjectUseCase].
 * Esta clase contiene los tests unitarios para el caso de uso que registra una nueva materia.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterOneSubjectUseCaseTest {

    private lateinit var registerOneSubjectUseCase: RegisterOneSubjectUseCase
    private val registerSubjectRepository: RegisterSubjectRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [RegisterOneSubjectUseCase] y sus dependencias.
     */
    @Before
    fun setUp() {
        registerOneSubjectUseCase = RegisterOneSubjectUseCase(registerSubjectRepository, preferenceUseCase)
    }

    /**
     * Test para verificar el caso de éxito del registro de una materia.
     */
    @Test
    fun `invoke con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        coEvery { registerSubjectRepository.executeRegisterOneSubject(any()) } returns ResultSuccess(emptyList())

        // Ejecutamos el método a probar
        val result = registerOneSubjectUseCase.invoke(mutableListOf(), "Matemáticas")

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }
}