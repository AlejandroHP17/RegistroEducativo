package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.student.RegisterStudentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.SuccessResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterOneStudentUseCase].
 * Verifica el comportamiento del caso de uso de registro de estudiante en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterOneStudentUseCaseTest {

    private lateinit var registerOneStudentUseCase: RegisterOneStudentUseCase
    private val crudStudentRepository: RegisterStudentRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        registerOneStudentUseCase = RegisterOneStudentUseCase(crudStudentRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de registro de estudiante exitoso.
     */
    @Test
    fun `invoke con datos validos debe devolver SuccessState`() = runBlocking {
        // Preparamos el mock
        coEvery { crudStudentRepository.executeRegisterOneStudent(any()) } returns ResultSuccess(listOf("Registro exitoso"))

        // Ejecutamos el caso de uso
        val result = registerOneStudentUseCase.invoke("Juan", "Perez", "Gomez", "XAXX010101HXXIXXA0", "2001-01-01", "1234567890")

        // Verificamos el resultado
        assertTrue(result is SuccessResult)
    }

    /**
     * Test para el flujo de registro de estudiante con error.
     */
    @Test
    fun `invoke con error del repositorio debe devolver ErrorUserState`() = runBlocking {
        // Preparamos el mock
        coEvery { crudStudentRepository.executeRegisterOneStudent(any()) } returns ResultError(FailureService.BadRequest)

        // Ejecutamos el caso de uso
        val result = registerOneStudentUseCase.invoke("Juan", "Perez", "Gomez", "XAXX010101HXXIXXA0", "2001-01-01", "1234567890")

        // Verificamos el resultado
        assertTrue(result is ErrorUserResult)
    }
}