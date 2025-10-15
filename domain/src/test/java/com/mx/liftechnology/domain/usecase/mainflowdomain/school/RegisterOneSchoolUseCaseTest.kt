package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.school.RegisterSchoolRepository
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
 * Tests para [RegisterOneSchoolUseCase].
 * Verifica el comportamiento del caso de uso de registro de escuela en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterOneSchoolUseCaseTest {

    private lateinit var registerOneSchoolUseCase: RegisterOneSchoolUseCase
    private val registerSchoolRepository: RegisterSchoolRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        registerOneSchoolUseCase = RegisterOneSchoolUseCase(registerSchoolRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de registro de escuela exitoso.
     */
    @Test
    fun `invoke con datos validos debe devolver SuccessState`() = runBlocking {
        // Preparamos el mock
        coEvery { registerSchoolRepository.executeRegisterOneSchool(any()) } returns ResultSuccess(listOf("Registro exitoso"))

        // Ejecutamos el caso de uso
        val result = registerOneSchoolUseCase.invoke("1234567890", 1, 1, "A", 1)

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }

    /**
     * Test para el flujo de registro de escuela con error.
     */
    @Test
    fun `invoke con error del repositorio debe devolver ErrorUserState`() = runBlocking {
        // Preparamos el mock
        coEvery { registerSchoolRepository.executeRegisterOneSchool(any()) } returns ResultError(FailureService.BadRequest)

        // Ejecutamos el caso de uso
        val result = registerOneSchoolUseCase.invoke("1234567890", 1, 1, "A", 1)

        // Verificamos el resultado
        assertTrue(result is ErrorUserState)
    }
}