package com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.formativeField.RegisterFormativeFieldsBulkRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.ModelSpinnersWorkMethods
import com.mx.liftechnology.domain.usecase.formativeField.RegisterFormativeFieldsBulkUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [com.mx.liftechnology.domain.usecase.formativeField.RegisterFormativeFieldsBulkUseCase].
 * Verifica el comportamiento del caso de uso de registro de materia en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterOneSubjectUseCaseTest {

    private lateinit var registerFormativeFieldsBulkUseCase: RegisterFormativeFieldsBulkUseCase
    private val registerFormativeFieldsBulkRepository: RegisterFormativeFieldsBulkRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        registerFormativeFieldsBulkUseCase = RegisterFormativeFieldsBulkUseCase(
            registerFormativeFieldsBulkRepository,
            preferenceUseCase
        )
    }

    /**
     * Test para el flujo de registro de materia exitoso.
     */
    @Test
    fun `invoke con datos validos debe devolver SuccessState`() = runBlocking {
        // Preparamos el mock
        val workMethods = mutableListOf(
            ModelSpinnersWorkMethods(1, 101, 1, ModelStateOutFieldText("Examen"), ModelStateOutFieldText("50"))
        )
        coEvery { registerFormativeFieldsBulkRepository.executeRegisterFormativeFieldsBulk(any()) } returns ResultSuccess(listOf("Registro exitoso"))

        // Ejecutamos el caso de uso
        val result = registerFormativeFieldsBulkUseCase.invoke(workMethods, "Matemáticas")

        // Verificamos el resultado
        assertTrue(result is SuccessResult)
    }

    /**
     * Test para el flujo de registro de materia con error.
     */
    @Test
    fun `invoke con error del repositorio debe devolver ErrorState`() = runBlocking {
        // Preparamos el mock
        coEvery { registerFormativeFieldsBulkRepository.executeRegisterFormativeFieldsBulk(any()) } returns ResultError(FailureService.ServerError)

        // Ejecutamos el caso de uso
        val result = registerFormativeFieldsBulkUseCase.invoke(null, "Matemáticas")

        // Verificamos el resultado
        assertTrue(result is ErrorResult)
    }
}