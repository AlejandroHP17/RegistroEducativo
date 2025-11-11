package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.schoolCycle.partial.RegisterListPartialRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.schoolCycle.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.RegisterListPartialUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [com.mx.liftechnology.domain.usecase.schoolCycle.partial.RegisterListPartialUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListPartialUseCaseTest {

    private lateinit var registerListPartialUseCase: RegisterListPartialUseCase
    private val registerListPartialRepository: RegisterListPartialRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        registerListPartialUseCase =
            RegisterListPartialUseCase(registerListPartialRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de registro de parciales exitoso.
     */
    @Test
    fun `invoke con datos validos devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        val adapterPeriods = listOf(ModelDatePeriodDomain(1, ModelStateOutFieldText("2024-01-01 / 2024-02-01"), 1))
        coEvery { registerListPartialRepository.executeRegisterListPartial(any()) } returns ResultSuccess(listOf("Éxito"))

        // Ejecutamos el caso de uso
        val result = registerListPartialUseCase.invoke(1, adapterPeriods)

        // Verificamos el resultado
        assertTrue(result is SuccessResult)
    }

    /**
     * Test para el caso en que el repositorio devuelve un error.
     */
    @Test
    fun `invoke con error del repositorio devuelve ErrorState`() = runBlocking {
        // Preparamos el mock
        coEvery { registerListPartialRepository.executeRegisterListPartial(any()) } returns ResultError(FailureService.ServerError)

        // Ejecutamos el caso de uso
        val result = registerListPartialUseCase.invoke(1, emptyList())

        // Verificamos el resultado
        assertTrue(result is ErrorResult)
    }
}